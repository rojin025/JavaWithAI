# ExceptionHandling Project

A comprehensive Java application demonstrating exception handling patterns, custom exceptions, and metrics tracking in a user registration workflow.

## Overview

This project implements a user registration system that showcases:
- **Custom Exception Classes** - Service-specific exception types for better error handling
- **Sequential Exception Handling** - Critical vs non-critical operation distinction
- **Metrics Tracking** - Success/failure rate monitoring for all service operations
- **Service Layer Architecture** - Separation of business logic into service classes

## Features

### Core Services

1. **UserService** - User creation with validation
   - Validates email, name, and password
   - Generates unique user IDs using UUID
   - Throws `UserServiceException` for validation failures

2. **EmailService** - Welcome email sending
   - Validates user data before sending
   - Simulates email sending process
   - Throws `EmailServiceException` for failures

3. **AuditService** - User creation event logging
   - Logs user creation events
   - Validates user data before logging
   - Throws `AuditServiceException` for failures

4. **MetricsService** - Success/failure metrics tracking
   - Tracks metrics for all service operations
   - Calculates success rates as percentages
   - Generates formatted metrics reports

### Custom Exception Classes

Located in `com.example.exceptions` package:
- `UserServiceException` - For user service errors
- `EmailServiceException` - For email service errors
- `AuditServiceException` - For audit service errors

All exception classes support:
- Message-only constructor
- Message-with-cause constructor (exception chaining)

### Exception Handling Strategy

**Critical Operations:**
- User creation - Must succeed before other operations
- Aborts entire process on failure

**Non-Critical Operations:**
- Email sending - Can fail without affecting user creation
- Audit logging - Can fail without affecting user creation
- Continue execution on failure

## Project Structure

```
ExceptionHandling/
├── src/
│   ├── App.java                                    # Main application
│   └── com/example/
│       ├── models/
│       │   ├── User.java                          # User domain model
│       │   └── UserData.java                      # User input DTO
│       ├── services/
│       │   ├── UserService.java                   # User creation service
│       │   ├── EmailService.java                  # Email sending service
│       │   ├── AuditService.java                  # Audit logging service
│       │   └── MetricsService.java                # Metrics tracking service
│       └── exceptions/
│           ├── UserServiceException.java          # User service exception
│           ├── EmailServiceException.java         # Email service exception
│           └── AuditServiceException.java        # Audit service exception
├── bin/                                            # Compiled classes
├── .vscode/
│   └── settings.json                               # VS Code configuration
└── README.md                                       # This file
```

## How to Run

### Compile
```bash
javac -d bin src/App.java src/com/example/**/*.java
```

### Execute
```bash
java -cp bin App
```

## Example Output

### Successful Execution
```
User created with ID: fc159fdf-140d-4b57-9936-7456b142c8ac
Sending welcome email to: john.doe@example.com
Subject: Welcome to our platform, John Doe!
Body: Thank you for joining us. Your account has been created successfully.
Welcome email sent successfully to john.doe@example.com
Logging user creation event...
User ID: fc159fdf-140d-4b57-9936-7456b142c8ac
User Email: john.doe@example.com
User Name: John Doe
Created At: 2026-01-26T21:15:03.862576
User creation logged successfully
User created successfully!

========================================
         SERVICE METRICS REPORT
========================================

UserService:
  Successes: 1
  Failures: 0
  Total Calls: 1
  Success Rate: 100.00%

EmailService:
  Successes: 1
  Failures: 0
  Total Calls: 1
  Success Rate: 100.00%

AuditService:
  Successes: 1
  Failures: 0
  Total Calls: 1
  Success Rate: 100.00%

========================================
Overall Success Rate: 100.00%
Total Calls: 3 | Successes: 3 | Failures: 0
========================================
```

## Key Concepts Demonstrated

- **Custom Exceptions** - Creating and using custom exception classes
- **Exception Propagation** - How exceptions propagate through the call stack
- **Try-Catch Blocks** - Handling exceptions with sequential try-catch blocks
- **Critical vs Non-Critical Operations** - Different handling strategies
- **Metrics Tracking** - Monitoring service health and reliability
- **Service Layer Pattern** - Separating business logic into services
- **DTO Pattern** - Using Data Transfer Objects for data transfer

## Documentation

Comprehensive documentation is available in:
- `Documents/Implementations/ExceptionHandling/`

Key documentation files:
- `PROJECT_INDEX.md` - Navigation hub for all documentation
- `ExceptionHandling_Implementation.md` - Complete implementation overview
- `ARCHITECTURE.md` - System architecture and data flow
- `CHANGELOG.md` - Project evolution and change history
- `MetricsService_Implementation.md` - Metrics tracking details
- `Exception_Classes_Implementation.md` - Custom exception classes details

## Version

**Current Version:** 3.0.0

**Features:**
- ✅ Custom exception classes
- ✅ Sequential try-catch blocks
- ✅ Metrics tracking
- ✅ Comprehensive documentation

## Future Enhancements

- Metrics persistence to file/database
- Time-based metrics (execution time tracking)
- Error categorization (tracking specific error types)
- Exception hierarchy (base ServiceException class)
- Email format validation
- Password hashing

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
