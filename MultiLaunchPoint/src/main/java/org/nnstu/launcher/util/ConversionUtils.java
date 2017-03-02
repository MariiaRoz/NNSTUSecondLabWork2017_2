package org.nnstu.launcher.util;

import com.jasongoodwin.monads.Try;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;
import org.nnstu.contract.AbstractServer;
import org.nnstu.launcher.structures.ServerDataModel;
import org.nnstu.launcher.structures.ServerStatus;
import org.nnstu.launcher.structures.immutable.RunnableServerInstance;
import org.nnstu.launcher.structures.immutable.ServerId;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Static class for different conversion tools
 *
 * @author Roman Khlebnov
 */
@Log4j
public class ConversionUtils {
    private ConversionUtils() {
    }

    /**
     * Method to convert reflected classes to a valid map for navigation and launching servers
     *
     * @param source {@link Set} of reflected classes
     * @return {@link Map} ready for work
     */
    public static Map<Integer, RunnableServerInstance> convertToRunnableInstances(Set<Class<? extends AbstractServer>> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyMap();
        }

        // All unsafe conversion will be safely wrapped into Try monad
        Map<Boolean, List<Try<RunnableServerInstance>>> conversion = source
                .stream()
                .map(Try::successful)
                .map(t -> t.map(ConversionUtils::unsafeConvertToRunnableInstance))
                .collect(Collectors.partitioningBy(Try::isSuccess));

        // Send all server initialization error to log
        conversion.get(false).forEach(t -> t.onFailure(log::error));

        Set<Integer> duplicateEliminator = new HashSet<>();
        return conversion
                .get(true)
                .stream()
                .filter(server -> {
                    AbstractServer instance = server.getUnchecked().getInstance();

                    if (!duplicateEliminator.add(instance.getServerPort())) {
                        log.error("Cannot add server instance " + convertToServerId(instance) + ", port is already bound!");
                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toMap(server -> server.getUnchecked().getInstance().getServerPort(), Try::getUnchecked));
    }

    /**
     * Method to convert any required object to {@link ServerId}
     *
     * @param source {@link Object} for conversion
     * @return {@link ServerId} for this object
     */
    public static ServerId convertToServerId(Object source) {
        if (source == null) {
            return ServerId.emptyServerId();
        }

        AbstractServer instance = null;

        if (source instanceof AbstractServer) {
            instance = (AbstractServer) source;
        } else if (source instanceof RunnableServerInstance) {
            instance = ((RunnableServerInstance) source).getInstance();
        }

        if (instance == null) {
            return ServerId.emptyServerId();
        } else {
            return new ServerId(
                    instance.getServerPort(),
                    instance.getClass().getSimpleName(),
                    instance.getServerMavenModuleName()
            );
        }
    }

    /**
     * Method to convert generic collection to an {@link ObservableList} for UI {@link javafx.scene.control.TableView}
     *
     * @param sourceElements {@link Collection} with generic elements
     * @param <T>            elements datatype
     * @return {@link ObservableList} with data for {@link javafx.scene.control.TableView}
     */
    public static <T> ObservableList<ServerDataModel> convertToObservableList(Collection<T> sourceElements) {
        if (sourceElements == null) {
            return FXCollections.emptyObservableList();
        }

        return FXCollections.observableArrayList(
                sourceElements
                        .stream()
                        .map(ConversionUtils::convertToDataModel)
                        .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                        .sorted()
                        .collect(Collectors.toList())
        );
    }

    /**
     * Private method, used primarily during conversion to {@link ObservableList}
     *
     * @param source {@link Object} to convert into {@link ServerDataModel}
     * @return {@link ServerDataModel} for {@link javafx.scene.control.TableView}
     */
    private static Optional<ServerDataModel> convertToDataModel(Object source) {
        ServerId id = convertToServerId(source);

        if (ServerId.emptyServerId().equals(id)) {
            return Optional.empty();
        } else {
            return Optional.of(new ServerDataModel(id, ServerStatus.STOPPED));
        }
    }

    /**
     * Private method to allow unsafe conversion to {@link RunnableServerInstance}
     *
     * @param genericInstance {@link Class} obtained during reflection
     * @return {@link RunnableServerInstance} for server management
     * @throws InstantiationException    - if the class that declares the underlying constructor represents an
     *                                   abstract class.
     * @throws IllegalAccessException    - if this {@code Constructor} object is enforcing Java language access
     *                                   control and the underlying constructor is inaccessible.
     * @throws InvocationTargetException - if the underlying constructor throws an exception.
     * @throws NoSuchMethodException     - if a matching method is not found.
     */
    private static RunnableServerInstance unsafeConvertToRunnableInstance(Class<? extends AbstractServer> genericInstance)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (genericInstance == null) {
            throw new IllegalArgumentException("Cannot convert provided server to runnable server instance.");
        }

        return new RunnableServerInstance(genericInstance.getConstructor().newInstance());
    }
}
