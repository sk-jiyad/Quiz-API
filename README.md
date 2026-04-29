# Quiz API

A RESTful backend service built with Spring Boot for managing and serving programming quizzes. Supports full CRUD on questions, randomized quiz generation by difficulty, and answer submission with automatic score calculation.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.3
- **Persistence:** Spring Data JPA, PostgreSQL
- **Build Tool:** Maven
- **Utilities:** Lombok
- **Testing:** Postman

## Architecture

Follows a clean layered MVC architecture with clear separation of concerns:

```
com.jiyad/
├── controller/    # REST endpoints (QuestionController, QuizController)
├── service/       # Business logic (QuestionService, QuizService)
├── dao/           # Data access layer via Spring Data JPA
├── model/         # JPA entities (Question, Quiz, QuestionWrapper, QuestionResponse)
└── exception/     # Global exception handling (GlobalExceptionHandler)
```

## Features

- Full CRUD operations for quiz questions
- Filter questions by difficulty level (Easy / Medium / Hard)
- **Randomized quiz generation** using a native PostgreSQL query (`ORDER BY RANDOM()`)
- **Answer hiding via DTO** — `QuestionWrapper` exposes questions to quiz takers without leaking the correct answer
- Submit quiz answers and receive a calculated score
- Global exception handling with custom `NotEnoughQuestionsException` for graceful error responses

## API Endpoints

### Questions

| Method | Endpoint                                  | Description                        |
|--------|-------------------------------------------|------------------------------------|
| GET    | `/question/allQuestions`                  | Get all questions (ordered by id)  |
| GET    | `/question/difficulty_level/{level}`      | Filter by difficulty (Easy/Medium/Hard) |
| POST   | `/question/post`                          | Add a new question                 |
| PUT    | `/question/update/{id}`                   | Partial update an existing question |
| DELETE | `/question/delete`                        | Delete a question by id            |

### Quiz

| Method | Endpoint              | Description                                          |
|--------|-----------------------|------------------------------------------------------|
| POST   | `/quiz/create`        | Create a quiz (`?diff=Easy&numQ=5&title=MyQuiz`)     |
| GET    | `/quiz/get/{id}`      | Fetch quiz questions (answers hidden via DTO)        |
| POST   | `/quiz/submit/{id}`   | Submit answers and receive score                     |

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 14+

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/sk-jiyad/Quiz-API.git
   cd Quiz-API/quizapp
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE questiondb;
   ```

3. **Configure credentials**

   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/questiondb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   The API starts on `http://localhost:8080`.

## Sample Usage

**Add a question:**
```bash
curl -X POST http://localhost:8080/question/post \
  -H "Content-Type: application/json" \
  -d '{
    "questionTitle": "What is the time complexity of binary search?",
    "option1": "O(n)",
    "option2": "O(log n)",
    "option3": "O(n^2)",
    "option4": "O(1)",
    "rightAnswer": "O(log n)",
    "difficultyLevel": "Easy"
  }'
```

**Create a quiz with 5 random Easy questions:**
```bash
curl -X POST "http://localhost:8080/quiz/create?diff=Easy&numQ=5&title=JavaBasics"
```

**Submit answers:**
```bash
curl -X POST http://localhost:8080/quiz/submit/1 \
  -H "Content-Type: application/json" \
  -d '[{"id":1,"response":"O(log n)"},{"id":2,"response":"Heap"}]'
```

## Design Highlights

- **DTO pattern with `QuestionWrapper`** — decouples the internal `Question` entity from the quiz-taking API, preventing answer leakage to clients
- **Native PostgreSQL query** for randomized question selection: `SELECT * FROM question WHERE difficulty_level=:diff ORDER BY RANDOM() LIMIT :numQ`
- **Partial update support** in `PUT /question/update/{id}` — only provided fields are updated, existing values are preserved
- **Global exception handling** with `@RestControllerAdvice` returning structured JSON error responses with timestamp, status, and message

## Author

**SK Jiyad**
- GitHub: [@sk-jiyad](https://github.com/sk-jiyad)
- LinkedIn: [sk-jiyad](https://linkedin.com/in/sk-jiyad)
