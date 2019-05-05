package com.alex.astraproject.shared.services;

public interface Mutable<E> {
	void createOne(E event);

	void updateById(E event);

	void deleteById(E event);

}
