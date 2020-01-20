# JEVENTBUS

JEventbus is a module that allows you to implement Event Driven Design approach to your classical code base.
JEventbus is calling specified methods of already registered listener classes by using Java Reflection.

JEventbus is ready to use in Java Module System.

## PROS

- Provides Single Responsbility Principle automatically
- Segregate logics that is not the part of main transaction or bounded-context
- Strongly decoupled business logics (bounded-contexts)
- Allows you to apply Domain Driven Design to monolith application
- Allows you to be prepared for migration from monolith application to microservice architecture
- Ready to use on Java Module System

## CONS

- You have to define events as enum
- You have to register listeners to events
- You have to relate listeners to events while initializing application

## CODE SAMPLES
```java
Events.event(PUBLISH_LISTING)
      .add(getBean(FeedLogger.class))
      .add(getBean(AuditLogger.class))
      .add(getBean(ListingBadgeService.class))
      .add(listingCacheManager)
      .add(getBean(PriceHistoryUpdater.class))
      .add(getBean(PriceChangeDetector.class))
      .add(getBean(ListingCardCacheLoader.class))
      .add(getBean(ListingDetailCacheLoader.class));
```
