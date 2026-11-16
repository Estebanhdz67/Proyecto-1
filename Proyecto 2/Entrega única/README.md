# Proyecto 2 – Club Deportivo Séneca

**ISIS-1226 Diseño y Programación Orientado a Objetos · Universidad de los Andes · 2026-20**

Integrantes: Esteban David Hernández Arias, Gabriel Esteban Ortegón Crespo, César Andrey Sanmiguel Mahecha

En esta carpeta está todo lo de la entrega única del Proyecto 2: el proyecto Eclipse con el código y las pruebas, las historias de usuario, el documento de diseño y los diagramas (archivos fuente `.drawio` e imágenes `.png`).

## Contenido

| Ruta | Qué hay |
|---|---|
| `ClubDeportivoSeneca/` | Proyecto Eclipse (se importa tal cual). |
| `ClubDeportivoSeneca/src/logica` | Lógica del club: `ClubDeportivoSeneca` (fachada) y las clases del dominio. |
| `ClubDeportivoSeneca/src/presentacion` | Interfaces por consola: un main por tipo de usuario. |
| `ClubDeportivoSeneca/src/persistencia` | Lectura y escritura de archivos. |
| `ClubDeportivoSeneca/src/test` | Pruebas JUnit 5 (unitarias y de integración). |
| `ClubDeportivoSeneca/datos` | Archivos de datos que la aplicación carga y guarda automáticamente. |
| `ClubDeportivoSeneca/Historias De Usuario/HU.docx` | Historias de usuario: banco oficial retomado y nuevas historias de torneos. |
| `ClubDeportivoSeneca/Diagramas y Analisis/Analisis/Documento de Diseño Proyecto 2.docx` | Documento de diseño actualizado (incluye el anexo de uso de IA). |
| `ClubDeportivoSeneca/Diagramas y Analisis/Alto Nivel` | Diagramas de alto nivel (`.drawio` y `.png`). |
| `ClubDeportivoSeneca/Diagramas y Analisis/Completo` | Diagramas de clases detallados (`.drawio` y `.png`). |
| `ClubDeportivoSeneca/Diagramas y Analisis/Secuencias` | Diagramas de secuencia del Proyecto 1. |

## Requisitos

- Eclipse IDE for Java Developers.
- JDK 21 (el proyecto está configurado con JavaSE-21).
- JUnit 5 (ya viene incluido en Eclipse; el proyecto lo tiene en el build path).

## Cómo importar el proyecto en Eclipse

1. **File → Import → General → Existing Projects into Workspace**.
2. En *Select root directory* escoger la carpeta `Entrega única/ClubDeportivoSeneca` (la que tiene el archivo `.project`).
3. **Finish**. Deben aparecer los paquetes `logica`, `persistencia`, `presentacion` y `test` dentro de `src`.

Si el proyecto sale con errores, revisar en **Properties → Java Build Path** que estén el JRE 21 y JUnit 5.

## Cómo ejecutar la aplicación

Hay un main para cada tipo de usuario. Se ejecutan con clic derecho → **Run As → Java Application**:

| Tipo de usuario | Clase |
|---|---|
| Socio | `presentacion.PrincipalSocio` |
| Empleado (entrenador o fisioterapeuta) | `presentacion.PrincipalEmpleado` |
| Administrador | `presentacion.PrincipalAdministrador` |

Al iniciar, la aplicación carga los datos de la carpeta `datos/` y pide login y password. Al salir (opción 0 del menú principal) guarda los cambios en esos mismos archivos. Si los archivos no existen, se cargan unos datos iniciales.

### Usuarios de prueba (todos con password `123`)

| Tipo | Logins |
|---|---|
| Administrador | `admin` |
| Entrenadores | `ancelotti`, `guardiola` |
| Fisioterapeuta | `fisio` |
| Socios | `pau`, `messi`, `cr7`, `neymar`, `mbappe`, `bellingham`, `vinicius`, `lamine`, `pedri`, `gavi`, `lewandowski`, `raphinha`, `dejong`, `terstegen` |

Los datos de prueba también traen disciplinas (Tenis, Padel, TenisMesa), instalaciones (`Cancha Rafa Nadal`, `Cancha Federer`, `Cancha Padel 1`, `Mesa 1`, `Santiago Bernabeu`), dos equipos Sub17, productos de tienda y cafetería, inventario en `Tienda Principal` y `Bodega`, turnos y dos torneos (`T1` recreativo de tenis y `T2` competitivo de fútbol).

Las fechas se escriben como `AAAA-MM-DD` y las horas como `HH:MM`. Si un dato está mal escrito, la consola lo vuelve a pedir.

## Cómo correr las pruebas

Clic derecho sobre la carpeta `src/test` (o sobre el proyecto) → **Run As → JUnit Test**. Son 166 pruebas en 22 clases y todas deben quedar en verde. La prueba de persistencia guarda una copia de los archivos de `datos/` y los restaura al terminar, así que correr las pruebas no daña los datos.
