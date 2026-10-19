package com.autobots.automanager.common;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.core.ResolvableType;
import org.springframework.hateoas.AffordanceModel.PayloadMetadata;
import org.springframework.hateoas.AffordanceModel.PropertyMetadata;
import org.springframework.hateoas.mediatype.PropertyUtils;

public record RecordInputMetadata(Class<?> type) implements PayloadMetadata {
    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Stream<PropertyMetadata> stream() {
        return PropertyUtils.getExposedProperties(type).stream()
                .map(property -> type.isRecord() ? new RecordProperty(property) : property);
    }

    private record RecordProperty(PropertyMetadata delegate) implements PropertyMetadata {
        private boolean text() {
            return String.class.equals(getType().resolve());
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public boolean isRequired() {
            return delegate.isRequired();
        }

        @Override
        public boolean isReadOnly() {
            return false;
        }

        @Override
        public Optional<String> getPattern() {
            return delegate.getPattern();
        }

        @Override
        public ResolvableType getType() {
            return delegate.getType();
        }

        @Override
        public String getInputType() {
            return text() ? "text" : delegate.getInputType();
        }

        @Override
        public Number getMin() {
            return text() ? null : delegate.getMin();
        }

        @Override
        public Number getMax() {
            return text() ? null : delegate.getMax();
        }

        @Override
        public Long getMinLength() {
            if (text() && delegate.getMin() != null) {
                return delegate.getMin().longValue();
            }
            return delegate.getMinLength();
        }

        @Override
        public Long getMaxLength() {
            if (text() && delegate.getMax() != null) {
                return delegate.getMax().longValue();
            }
            return delegate.getMaxLength();
        }
    }
}
