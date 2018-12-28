package com.auth.authsec.domain.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class for converting 'LocalDateTime' objects for persistence and management
 * in the database and backwards conversion for the domain level.
 *
 * @see @Converter
 * @author Plamen
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * Converts a LocalDateTime object to a SQL Timestamp object, so that it can
     * be persisted and managed in the database.
     *
     * @param entityAttribute
     * @return converted SQL data type
     */
    @Override
    public final Timestamp convertToDatabaseColumn(LocalDateTime entityAttribute) {
        return Timestamp.valueOf(entityAttribute);
    }

    /**
     * Converts a SQL Timestamp object to a LocalDateTime object.
     *
     * @param databaseEntity
     * @return converted object data type
     */
    @Override
    public final LocalDateTime convertToEntityAttribute(Timestamp databaseColumn) {
        return databaseColumn.toLocalDateTime();
    }

}
