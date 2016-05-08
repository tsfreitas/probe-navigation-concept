package com.tsfreitas.probe.rest.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tsfreitas.probe.exception.AlreadyExistProbeException;
import com.tsfreitas.probe.exception.MissionNotStartedException;
import com.tsfreitas.probe.exception.ProbeNotExistsException;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissionNotStartedException.class)
	@ResponseBody
	public ErrorInfo missionNotStartedException(MissionNotStartedException ex) {
		String message = "Mission not started. First call POST /mission/startMission";
		return new ErrorInfo("MissionNotStartedException", message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AlreadyExistProbeException.class)
	@ResponseBody
	public ErrorInfo alreadyExistProbeException(AlreadyExistProbeException ex) {
		String message = "Probe already exists";
		return new ErrorInfo("AlreadyExistProbeException", message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProbeNotExistsException.class)
	@ResponseBody
	public ErrorInfo ProbeNotExistsException(ProbeNotExistsException ex) {
		String message = "Probe %s not exists";
		return new ErrorInfo("AlreadyExistProbeException", String.format(message, ex.getProbeName()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ErrorInfo requiredFieldNotFilled(MethodArgumentNotValidException ex) {
		String message = "Required field not provided";
		ErrorInfo ei = new ErrorInfo("MethodArgumentNotValidException", message);

		ex.getBindingResult().getFieldErrors().forEach(
				fieldError -> ei.addFieldError(new FieldError(fieldError.getField(), fieldError.getDefaultMessage())));

		return ei;
	}

}

class ErrorInfo {
	private String exception;

	private String detailedMessage;

	private List<FieldError> fieldErros = new ArrayList<>();

	public ErrorInfo(String exception, String detailedMessage) {
		this.exception = exception;
		this.detailedMessage = detailedMessage;
	}

	public String getException() {
		return exception;
	}

	public String getDetailedMessage() {
		return detailedMessage;
	}

	public List<FieldError> getFieldErros() {
		return fieldErros;
	}

	public void addFieldError(FieldError fieldError) {
		fieldErros.add(fieldError);
	}

}

class FieldError {

	private String field;

	private String message;

	public FieldError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

}
