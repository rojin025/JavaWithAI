# JavaWithAI

Learning Java with AI-powered Development

## Projects

### HelloWorld Project

This project demonstrates fundamental Java concepts and best practices:

#### Core Features

- **App.java**: Main application showcasing:
  - Basic data types and variables (String, int, double, boolean, LocalDate)
  - Collections framework (List, Map, Set)
  - Exception handling with try-catch blocks
  - Type parsing (Integer, Double, Boolean)

- **DataProcessor.java**: Utility class for statistical calculations:
  - Percentile calculation using nearest-rank method with linear interpolation
  - Sequential and parallel processing implementations
  - Edge case handling and input validation

- **LibraryBranch.java**: Object-oriented programming example:
  - Class with private fields, constructors, getters, and setters
  - Package structure (`com.library.model`)
  - Business logic methods (`isOpen()`)
  - Overridden `toString()` method

#### Project Structure

```
JavaWithAI/HelloWorld/
├── src/           # Source code
├── bin/           # Compiled classes
└── README.md      # Project documentation
```

---

### ExceptionHandling Project

A comprehensive Java application demonstrating exception handling patterns, custom exceptions, and metrics tracking.

#### Core Features

- **Custom Exception Classes**: Service-specific exception types (`UserServiceException`, `EmailServiceException`, `AuditServiceException`)
- **Sequential Exception Handling**: Critical vs non-critical operation distinction
- **Metrics Tracking**: Success/failure rate monitoring via `MetricsService`
- **Service Layer Architecture**: Separation of business logic into service classes

#### Services

1. **UserService** - User creation with validation
2. **EmailService** - Welcome email sending
3. **AuditService** - User creation event logging
4. **MetricsService** - Success/failure metrics tracking

#### Key Concepts

- Custom exception classes with exception chaining support
- Sequential try-catch blocks for different criticality levels
- Metrics tracking and reporting
- Service layer pattern
- DTO pattern (UserData)

#### Project Structure

```
JavaWithAI/ExceptionHandling/
├── src/
│   ├── App.java
│   └── com/example/
│       ├── models/          # User and UserData models
│       ├── services/        # Business logic services
│       └── exceptions/      # Custom exception classes
├── bin/                     # Compiled classes
└── README.md               # Project documentation
```

#### Version

**Current Version:** 3.0.0

**Features:**
- ✅ Custom exception classes
- ✅ Sequential try-catch blocks
- ✅ Metrics tracking
- ✅ Comprehensive documentation

For detailed documentation, see `Documents/Implementations/ExceptionHandling/`

---

### UserManagementSystem Project

A Java application demonstrating **inheritance** and **polymorphism** through a user management system with different user tiers and activity tracking.

#### Core Features

- **User Hierarchy**: Abstract `User` base class with three subclasses (`FreeUser`, `PremiumUser`, `EnterpriseUser`)
- **Polymorphism**: Same interface, different implementations per user type
- **Authentication**: Password-based with optional 2FA/SSO per tier
- **Permission System**: Enum-based permissions with tier-specific access levels
- **Billing**: Different models (free, subscription $9.99/month, custom pricing $299.99+/month)
- **Rate Limiting**: Daily operation limits (10, 100, unlimited) based on tier
- **Activity Tracking**: Automatic logging with statistics and analytics

#### Key Concepts

- Inheritance hierarchy with abstract base class
- Abstract methods (`hasPermission()`, `calculateBilling()`, `getUserType()`)
- Method overriding for specialized behavior
- Polymorphic method calls (same interface, different implementations)
- Activity tracking system with filtering and statistics
- Design patterns (Template Method, Strategy)

#### Project Structure

```
JavaWithAI/UserManagementSystem/
├── src/
│   ├── App.java
│   └── com/example/usermanagement/
│       ├── Permission.java          # Permission enum
│       ├── User.java                # Abstract base class
│       ├── FreeUser.java            # Free tier
│       ├── PremiumUser.java         # Premium tier
│       ├── EnterpriseUser.java      # Enterprise tier
│       ├── Activity.java            # Activity entity
│       ├── ActivityType.java        # Activity type enum
│       └── ActivityTracker.java     # Activity manager
├── bin/                             # Compiled classes
└── README.md                        # Project documentation
```

#### Version

**Current Version:** 1.1.0

**Features:**
- ✅ Inheritance hierarchy (User → FreeUser, PremiumUser, EnterpriseUser)
- ✅ Polymorphism demonstration
- ✅ Authentication with 2FA/SSO support
- ✅ Permission-based access control
- ✅ Rate limiting per user tier
- ✅ Activity tracking system
- ✅ Comprehensive documentation

For detailed documentation, see `Documents/Implementations/UserManagementSystem/`
