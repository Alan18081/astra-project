package com.alex.astraproject.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEventEntity {

	private Object data;

	private long revision;

	private long timestamp;

	private String type;

}
