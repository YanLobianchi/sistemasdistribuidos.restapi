package com.sistemasdistribuidos.restapi.exception;

import java.util.Arrays;

public class NotFoundException extends RuntimeException {

	public NotFoundException(final Class<?> clazz, final Long ...id) {
		super(clazz.getSimpleName() + " de ID=" + Arrays.toString(id) + " não encontrado");
	}
}
