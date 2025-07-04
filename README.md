# labseq

<details>
  <summary>Quarkus Starter Info</summary>

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/labseq-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
</details>

<details>
  <summary>Exercise - Laseq</summary>

### Exercise Details:
- Implement a REST service, using the Quarkus JAVA framework, returning a value from the
labseq sequence.
- Optionally implement a simple JavaScript1 web GUI to invoke the service.
- The labseq – l(n) - sequence is defined as follows:
- n=0 => l(0) = 0
- n=1 => l(1) = 1
- n=2 => l(2) = 0
- n=3 => l(3) = 1
- n>3 => l(n) = l(n-4) + l(n-3)
- Example of the first sequence values:
    ```
     0, 1, 0, 1, 1, 1, 1, 2, 2, 2, 3, ...
     ```
- The endpoint created should be in the form <baseurl>/labseq/{n} where {n}
represents the index of the sequence’s (single) value to return. 
- The index may be any nonnegative integer number.

## 1. Preparation
   - Install java 21.
   - Optionally install Quarkus CLI.

## 2. Run Docker Compose
   - Start the Jaeger UI using Docker Compose:
     ```shell
     docker compose up -d
     ```
   - This will start Jaeger UI at port 16686. You can access it at <http://localhost:16686>.
   - It contains telemetry data for the application, which can be useful for debugging and monitoring.

## 3. Run the application
To run the application, you can use the Quarkus CLI or Maven.
   - If you have the Quarkus CLI installed, you can run the application using:
     ```shell
     quarkus dev
     ```
   - If you prefer Maven, you can run the application using:
     ```shell
     ./mvnw quarkus:dev
     ```

## 4. Test the application
   - There are tests included in the project that you can run to verify the functionality. For the endpoint and for the service funtion.
   - Swagger UI is available at:
       ```
       http://localhost:8080/swagger-ui/
       ```
   - You can use it to test the endpoint.
   - Using postman or similar tool, you can test the endpoint by sending a GET request to:
     ```
     http://localhost:8080/labseq/{n}
     ```
     where `{n}` is the index of the sequence value you want to retrieve. 

## 5. Optional: Implement a simple JavaScript web GUI
   - You can create a simple HTML page with a form to input the index and a button to fetch the value from the REST service.
   - I was not done because of the short time available.
   - If a frontend is implemented CORS should be enabled for the frontend to access the REST service.

## 6. About the labseq sequence
   - The labseq sequence is defined as follows:
     - n=0 => l(0) = 0
     - n=1 => l(1) = 1
     - n=2 => l(2) = 0
     - n=3 => l(3) = 1
     - n>3 => l(n) = l(n-4) + l(n-3)
   - The first few values of the sequence are:
     ```
     0, 1, 0, 1, 1, 1, 1, 2, 2, 2, 3, ...
     ```
   - The controller receives a non-negative integer `n` and returns the value of the sequence at that index.
   - If `n` is larger than 500000, the service returns a 400 Bad Request response. This is to prevent OutOfMemoryError or performance issues with large values of `n`.
   - The project uses Logger to log the requests and responses, which can be useful for debugging and monitoring.
   - If the index is negative, the service should return a 400 Bad Request response.
   - It passes the request to the service, which calculates the value based on the sequence definition.
   - The algorithm is implemented in the `LabseqService` class, which contains a method `getValue(int n)` that returns the value of the sequence at index `n`.


   - The main logic is first it verifies if the `n` is between 0 and 3, if so it returns the value directly. If `n` is greater than 3, it recursively calculates the value using the formula `l(n) = l(n-4) + l(n-3)`.
   - At the for cicle `i` is sequentially set to `n-4` and `n-3` and the values are saved at the Repository HashMap.
   - The Repository is used to store the values of the sequence, so that they can be reused in subsequent requests, improving performance.

   - A interface `CacheRepository` was created to define the methods for the repository, and a class `InMemoryCacheImplementation` implements this interface using a HashMap to store the values.
   - This was made to respect the single responsibility principle and to allow for easy replacement of the repository implementation if needed in the future.
   - The Hashmap is on its own class as a Singleton, so it is shared across all requests. It also respects the single responsibility principle
   - The service is also designed to handle large values of `n` efficiently by using a recursive approach with memorization.

## 7. Final Notes
- The project is structured to follow the best practices of Quarkus and Java development.
- On a single Thread, the service can handle requests for `n` up to a large number without blocking the main thread. This will depend on the available memory and the performance of the machine running the service.
- If a sequential multithreading is needed, the service can be modified to use a thread pool or other concurrency mechanisms.
- That way the service can handle larger values of `n` without blocking the main thread.
- Of course because after the first request of a number because all positions are saved in the repository, the next request for the same number and under it will be very fast.

</details>

This exercise was made by Simão Campos as request by Pedro Cordeiro from Dellent. 



