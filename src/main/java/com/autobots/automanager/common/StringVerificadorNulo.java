package com.autobots.automanager.common;

public class StringVerificadorNulo {

	public static boolean verificar(String dado) {
		return dado == null || dado.isBlank();
	}
}