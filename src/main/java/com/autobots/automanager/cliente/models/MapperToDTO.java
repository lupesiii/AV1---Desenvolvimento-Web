package com.autobots.automanager.cliente.models;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.domain.EntidadeBase;

@Component
public class MapperToDTO {
  public <T extends EntidadeBase, V> V toDTO(T entidade, Class<V> dtoClass) {
    if (!dtoClass.isRecord()) {
      throw new IllegalArgumentException("O DTO deve ser um record");
    }

    if (entidade == null) {
      return null;
    }

    return dtoClass.cast(mapearRecord(entidade, dtoClass));
  }

  private Object mapearRecord(Object entidade, Class<?> dtoClass) {
    try {
      RecordComponent[] componentes = dtoClass.getRecordComponents();
      PropertyDescriptor[] propriedades = Introspector.getBeanInfo(entidade.getClass()).getPropertyDescriptors();

      Class<?>[] tipos = new Class<?>[componentes.length];
      Object[] valores = new Object[componentes.length];

      for (int i = 0; i < componentes.length; i++) {
        RecordComponent componente = componentes[i];
        PropertyDescriptor propriedade = Arrays.stream(propriedades)
            .filter(f -> f.getName().equals(componente.getName()))
            .findFirst()
            .orElse(null);

        if (propriedade == null || propriedade.getReadMethod() == null) {
          throw new IllegalArgumentException(
              "Propriedade sem getter: " + entidade.getClass().getSimpleName() + "."
                  + propriedade.getReadMethod().getName());
        }

        Object valor = propriedade.getReadMethod().invoke(entidade);
        tipos[i] = componente.getType();

        try {
          valores[i] = valor.toString();
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Erro na conversão dos valores: "
              + dtoClass.getSimpleName()
              + "." + componente.getName());
        }
      }

      return dtoClass.getDeclaredConstructor(tipos).newInstance(valores);

    } catch (ReflectiveOperationException | IntrospectionException e) {
      throw new IllegalStateException(
          "Erro ao converter para " + dtoClass.getSimpleName(), e);
    }
  }
}