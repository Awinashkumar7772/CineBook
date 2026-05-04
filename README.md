# CineBook — Movie Ticket Booking System

A full-stack movie ticket booking web application built with Java 21 
and Spring Boot 3.x. Covers end-to-end booking flow including 
authentication, seat selection, payment, and email confirmation.

## Tech Stack

**Backend:** Java 21, Spring Boot 3.x, Spring Security, JWT, 
Spring Data JPA, Hibernate, MySQL, Redis

**Frontend:** React.js, Tailwind CSS, Axios

**Integrations:** Razorpay Payment Gateway, Gmail SMTP, 
Google Gemini AI API

## Features

- JWT-based authentication with role-based access control 
  (USER / ADMIN)
- Movie, theatre, screen, and show management via Admin panel
- Interactive seat map with live availability updates 
  using Server-Sent Events
- Redis-based seat locking with 10-minute TTL to prevent 
  double booking race conditions
- Razorpay payment integration with HMAC-SHA256 
  signature verification
- Automated booking confirmation email with QR code ticket
- AI-powered seat recommendation using Google Gemini API
- Waitlist system for houseful shows using Redis queue

## Database Schema

9 tables: users, movies, theatres, screens, seats, shows, 
bookings, booking_seats, payments

## API Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | /api/auth/register | Register new user | Public |
| POST | /api/auth/login | Login and get JWT | Public |
| GET | /api/movies | Get all movies | Public |
| GET | /api/movies/{id}/shows | Get shows for movie | Public |
| POST | /api/seats/lock | Lock selected seats | USER |
| POST | /api/bookings | Create booking | USER |
| POST | /api/payments/verify | Verify payment | USER |
| POST | /api/admin/movies | Add movie | ADMIN |
| POST | /api/admin/shows | Create show | ADMIN |

## How to Run Locally

**Prerequisites:** Java 21, MySQL, Redis, Maven

**1. Clone the repository**
git clone https://github.com/Awinashkumar7772/cinebook-backend.git

**2. Create MySQL database**
CREATE DATABASE cinebook;

**3. Configure application.properties**
Update src/main/resources/application.properties with your 
MySQL password, Gmail credentials, and Razorpay keys.

**4. Start Redis**
redis-server

**5. Run the application**
mvn spring-boot:run

Server starts on http://localhost:8080

## Project Structure

src/main/java/com/cinebook/
├── config/          Spring Security, Redis, CORS config
├── controller/      REST API endpoints
├── service/         Business logic
├── repository/      Database queries
├── entity/          JPA entities (database tables)
├── dto/             Request and response objects
├── security/        JWT filter and utility
└── exception/       Custom exceptions and global handler
