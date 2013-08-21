package kr.june.site.helper.model;

import java.util.List;

import lombok.Data;

@Data
public class ValidationResponse {
	private String status;
	private List errorMessageList;

}
