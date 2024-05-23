package repository.IT;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class AbstractContainerIT
{
    private static final DockerImageName myImage = DockerImageName
            .parse("red-queen-prototype-db:latest")
            .asCompatibleSubstituteFor("postgres");

    static int containerPort = 5432 ;

    static int localPort = 5432 ;

    protected static PostgreSQLContainer<?> dbContainer = new  PostgreSQLContainer<>(myImage)
            .withExposedPorts(containerPort)
            .withDatabaseName("teamo_db")
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(
                            new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withReuse(true)
            .waitingFor(
                    Wait.forLogMessage(".*ready to accept connections.*\\n", 1)
            );

    @BeforeAll
    static void startDBContainer()
    {
        dbContainer.start();
    }
}
