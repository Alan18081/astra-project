package com.alex.astraproject.shared.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEvent {

	private String id;

	private String type;

	private Map<String, Object> data;

	private long revision;

	private long timestamp;

}
