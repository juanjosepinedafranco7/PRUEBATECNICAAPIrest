# Respuestas Teóricas - Prueba Técnica API Tareas

### 1. ¿Cuál es la diferencia entre @RestController y @Controller en Spring Boot?
@Controller se utiliza en la arquitectura MVC tradicional para retornar vistas HTML completas (usando motores de plantillas como Thymeleaf). Por otro lado, @RestController es una anotación compuesta que incluye @Controller y @ResponseBody. Su función es devolver directamente los datos de los objetos en el cuerpo de la respuesta HTTP, convirtiéndolos automáticamente a formato JSON o XML, ideal para el desarrollo de APIs REST.

### 2. ¿Por qué se usan DTOs en lugar de exponer directamente la entidad JPA?
Se usan por tres razones fundamentales:
* **Seguridad (Mass Assignment):** Evita que un cliente manipule campos internos o sensibles (como contraseñas o campos de auditoría) enviando propiedades adicionales en el JSON.
* **Desacoplamiento:** Separa la estructura física de la base de datos del contrato expuesto al cliente. Si la tabla cambia, la API externa puede seguir funcionando igual.
* **Optimización de datos:** Permite enviar únicamente las propiedades que el frontend necesita consumir, ahorrando ancho de banda.

### 3. ¿Qué ventaja tiene @PrePersist sobre asignar la fecha en el constructor de la entidad?
La ventaja de @PrePersist es que está integrado directamente en el ciclo de vida de persistencia de JPA. Un constructor de Java se ejecuta cada vez que creamos una instancia en memoria, incluso si es para una consulta o manipulación temporal. En cambio, @PrePersist se dispara única y exclusivamente un instante antes de que el motor realice la inserción real (INSERT INTO) en la base de datos, garantizando que la estampa de tiempo refleje el momento exacto del registro y delegando esa responsabilidad directamente al framework de persistencia.

### 4. ¿Qué diferencia hay entre spring.jpa.hibernate.ddl-auto=update y ddl-auto=create? ¿Cuál usarías en producción y por qué?
* create: Borra todas las tablas existentes y las crea desde cero cada vez que la aplicación se inicia (provoca pérdida total de datos).
* update: Compara la estructura de las clases @Entity con las tablas de la base de datos y solo aplica modificaciones o crea las columnas faltantes, preservando los registros.

**En producción usaría la propiedad none o validate.** Nunca se debe usar update ni create en producción, ya que cambios automáticos imprevistos de Hibernate pueden dañar o alterar la integridad de datos críticos reales. Las bases de datos productivas deben gestionarse mediante scripts SQL manuales controlados o herramientas de migración como Flyway o Liquibase.

### 5. Si esta API fuera a producción con usuarios reales, menciona al menos 3 cambios que harías en la configuración o arquitectura.
1. **Seguridad (Spring Security + JWT):** Implementaría autenticación mediante Tokens JWT para restringir el acceso a los endpoints y asegurar que un usuario solo pueda ver o alterar sus propias tareas.
2. **Estrategia de ddl-auto:** Cambiaría el parámetro de Hibernate a validate o none e integraría una herramienta de control de versiones de base de datos como Liquibase.
3. **Manejo Centralizado de Excepciones:** Migraría los controles de error simples a un componente @RestControllerAdvice global para que los controladores no manejen bloques try-catch y las respuestas de error sean capturadas de manera uniforme a nivel de arquitectura.