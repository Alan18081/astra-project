package com.alex.astraproject.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseEventEntity {

	private Object data;

	private long revision;

	private long timestamp;
}
