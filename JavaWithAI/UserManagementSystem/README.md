# User Management System

A comprehensive Java application demonstrating **inheritance** and **polymorphism** through a user management system with different user tiers (Free, Premium, Enterprise). The system includes authentication, permission-based access control, billing, rate limiting, and activity tracking.

## 🎯 Project Overview

This project demonstrates core Object-Oriented Programming concepts:
- **Inheritance** - Class hierarchy with abstract base class and concrete subclasses
- **Polymorphism** - Same interface, different implementations per user type
- **Abstract Classes** - Base class defining contracts that subclasses must fulfill
- **Method Overriding** - Subclasses customize inherited behavior
- **Activity Tracking** - Comprehensive logging and analytics system

## ✨ Key Features

### User Types
- **FreeUser** - Limited permissions, 10 operations/day, no billing
- **PremiumUser** - Extended permissions, 100 operations/day, $9.99/month, optional 2FA
- **EnterpriseUser** - Full permissions, unlimited operations, custom pricing, mandatory 2FA + SSO

### Core Functionality
- **Authentication** - Password-based with optional 2FA/SSO
- **Permission System** - Enum-based permissions with different access levels per user type
- **Billing** - Different billing models (free, subscription, custom pricing)
- **Rate Limiting** - Daily operation limits based on user tier
- **Activity Tracking** - Automatic logging of user activities with statistics and analytics

## 🏗️ Architecture

### Inheritance Hierarchy

```
                    User (Abstract Base Class)
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
    FreeUser         PremiumUser      EnterpriseUser
   (Concrete)        (Concrete)       (Concrete)
```

### Design Patterns

- **Inheritance Pattern** - Base class with subclasses extending functionality
- **Polymorphism** - Abstract methods with different implementations
- **Template Method Pattern** - Base class defines structure, subclasses implement details
- **Strategy Pattern** - Different authentication/permission/billing strategies per user type

### Component Structure

```
com.example.usermanagement/
├── Permission.java          # Permission enum
├── User.java                # Abstract base class
├── FreeUser.java            # Free tier subclass
├── PremiumUser.java         # Premium tier subclass
├── EnterpriseUser.java      # Enterprise tier subclass
├── Activity.java            # Activity tracking entity
├── ActivityType.java        # Activity type enum
└── ActivityTracker.java     # Activity tracking manager
```

### Data Flow

1. **User Creation** → Constructor initializes base class → Subclass sets specific fields
2. **Authentication** → Base class verifies password → Subclass adds 2FA/SSO if needed → Activity tracked
3. **Permission Check** → Base class tracks activity → Subclass checks permission set → Returns result
4. **Operations** → Base class checks rate limit → Tracks activity → Returns success/failure

## 📚 Learning Objectives

### Core Concepts Demonstrated

1. **Inheritance Hierarchy**
   - Abstract base class (`User`) with common functionality
   - Concrete subclasses (`FreeUser`, `PremiumUser`, `EnterpriseUser`) extending base class
   - Code reuse through inheritance

2. **Abstract Classes and Methods**
   - Abstract methods: `hasPermission()`, `calculateBilling()`, `getUserType()`
   - Concrete methods: `authenticate()`, `canPerformOperation()`, `hashPassword()`
   - Subclasses must implement abstract methods

3. **Polymorphism**
   - All user types stored in `User[]` array
   - Same method calls produce different results based on actual object type
   - Runtime method resolution

4. **Method Overriding**
   - Subclasses override `authenticate()` for enhanced security (2FA, SSO)
   - Each subclass provides unique `calculateBilling()` implementation
   - `EnterpriseUser` overrides `canPerformOperation()` for unlimited operations

5. **Encapsulation**
   - `protected` fields accessible to subclasses
   - Public getters for controlled access
   - Private implementation details

### Key Principles

- **Inheritance is the Backbone of OOP** - Models "is-a" relationships, enables code reuse
- **Polymorphism: Same Interface, Different Behavior** - Uniform treatment with specialized implementations
- **Abstract Classes Define Contracts** - Specify required behavior while providing common implementation
- **Code Reuse Through Inheritance** - Common functionality in parent class, inherited by all subclasses
- **Specialization Through Overriding** - Customize behavior while maintaining interface

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- VS Code with Java Extension Pack (optional)

### Compilation

```bash
cd JavaWithAI/UserManagementSystem
javac -d bin src/App.java src/com/example/usermanagement/*.java
```

### Execution

```bash
java -cp bin App
```

### Expected Output

The demo application demonstrates:
- User information for each type
- Authentication tests (correct/wrong passwords)
- Permission checks for different user types
- Rate limiting behavior
- Billing information
- User-specific features (2FA, SSO, session timeouts)
- **Activity tracking statistics and history**

## 📁 Project Structure

```
UserManagementSystem/
├── .vscode/
│   └── settings.json          # VS Code Java configuration
├── bin/                       # Compiled bytecode (.class files)
├── src/                       # Source code
│   ├── App.java              # Main demo application
│   └── com/example/
│       └── usermanagement/    # User management package
│           ├── Permission.java      # Permission enum
│           ├── User.java            # Abstract base class
│           ├── FreeUser.java        # Free tier subclass
│           ├── PremiumUser.java     # Premium tier subclass
│           ├── EnterpriseUser.java  # Enterprise tier subclass
│           ├── Activity.java        # Activity tracking entity
│           ├── ActivityType.java    # Activity type enum
│           └── ActivityTracker.java # Activity tracking manager
└── README.md                  # This file
```

## 📖 Documentation

Comprehensive documentation is available in:
- `Documents/Implementations/UserManagementSystem/`

### Key Documentation Files

- **PROJECT_INDEX.md** - Navigation hub for all documentation
- **ARCHITECTURE.md** - Detailed system architecture and design patterns
- **LEARNING_OBJECTIVES_AND_PRINCIPLES.md** - Complete learning guide
- **CHANGELOG.md** - Project evolution and change history
- **UserManagementSystem_Implementation.md** - Complete implementation overview

### Component Documentation

- Permission_Enum_Implementation.md
- User_Base_Class_Implementation.md
- FreeUser_Implementation.md
- PremiumUser_Implementation.md
- EnterpriseUser_Implementation.md
- Activity_Class_Implementation.md
- ActivityType_Enum_Implementation.md
- ActivityTracker_Implementation.md
- App_Demo_Implementation.md

## 🎓 What You'll Learn

After exploring this project, you'll understand:
- How to create class hierarchies with inheritance
- Abstract classes and abstract methods
- Polymorphism and runtime method resolution
- Method overriding in subclasses
- Enum-based permission systems
- Activity tracking and analytics
- Design patterns (Template Method, Strategy)

## 🔧 Features by Version

### Version 1.0.0
- Base User class with inheritance hierarchy
- Three user subclasses (Free, Premium, Enterprise)
- Authentication system
- Permission-based access control
- Rate limiting
- Billing system

### Version 1.1.0
- **Activity tracking system**
- Automatic activity logging
- Activity statistics and analytics
- Activity filtering (by user, type, time range)
- Enhanced demo with tracking demonstrations

## 💡 Usage Example

```java
// Create user instances
FreeUser freeUser = new FreeUser("john_doe", "john@example.com", "password123");
PremiumUser premiumUser = new PremiumUser("jane_smith", "jane@example.com", "securePass456", true);
EnterpriseUser enterpriseUser = new EnterpriseUser("admin_corp", "admin@corp.com", "enterprise789", 299.99);

// Demonstrate polymorphism
User[] users = {freeUser, premiumUser, enterpriseUser};

for (User user : users) {
    // Same method call, different implementations
    System.out.println(user.getUserType());
    System.out.println(user.calculateBilling());
    System.out.println(user.hasPermission(Permission.ADMIN));
}

// Activity tracking
String stats = freeUser.getActivityStatistics();
List<Activity> history = freeUser.getActivityHistory();
```

## 📝 Notes

- Password hashing is simplified for demonstration (use BCrypt in production)
- Activity tracking uses in-memory storage (consider database persistence for production)
- All activities are automatically tracked for authentication, permissions, and operations

## 🔗 Related Projects

- **ExceptionHandling** - Exception handling patterns in Java
- **HelloWorld** - Basic Java concepts and project structure

---

**Version:** 1.1.0  
**Last Updated:** 2026-01-26  
**Status:** Active
