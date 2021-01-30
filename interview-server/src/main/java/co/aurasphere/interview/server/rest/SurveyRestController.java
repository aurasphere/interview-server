package co.aurasphere.interview.server.rest;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.rest.model.GetSurveyListResponse;
import co.aurasphere.interview.server.rest.model.GetSurveyResponse;
import co.aurasphere.interview.server.rest.model.GetTechListResponse;
import co.aurasphere.interview.server.rest.model.SubmitSurveyRequest;
import co.aurasphere.interview.server.rest.model.SubmitSurveyResponse;
import co.aurasphere.interview.server.service.InterviewService;

/**
 * REST controller for operations on a {@link Survey}.
 * 
 * @author Donato Rimenti
 */
@RestController
@RequestMapping("/survey")
public class SurveyRestController {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(SurveyRestController.class);

	/**
	 * The service.
	 */
	@Autowired
	private InterviewService service;

	/**
	 * Gets the list of available surveys for a user and a technology. Only the
	 * names are returned.
	 *
	 * @param technology
	 *            the technology of the surveys to retrieve
	 * @param username
	 *            the email of the user whose surveys needs to be retrieved
	 * @return a list of survey names
	 */
	@RequestMapping(value = "/getSurveyList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GetSurveyListResponse getSurveyList(@RequestParam("technology") String technology,
			@RequestParam("username") String username) {
		LOG.trace("SurveyRestController.getSurveyList({}, {})", technology, username);
		Set<String> surveyList = service.getAvailableSurveys(username, technology);
		return new GetSurveyListResponse(surveyList);
	}

	/**
	 * Gets a survey by its name.
	 *
	 * @param id
	 *            the name of the survey
	 * @return the survey
	 */
	@RequestMapping(value = "/getSurvey/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GetSurveyResponse getSurvey(@PathVariable("id") String id) {
		LOG.trace("SurveyRestController.getSurvey({})", id);
		Survey survey = service.getSurveyByName(id);
		return new GetSurveyResponse(survey);
	}

	/**
	 * Submits a survey and returns the correct answers for that survey.
	 *
	 * @param request
	 *            contains the data of the submitted survey
	 * @return the survey submitted with the correct answers
	 */
	@RequestMapping(value = "/submitSurvey", method = RequestMethod.POST)
	public @ResponseBody SubmitSurveyResponse submitSurvey(@RequestBody SubmitSurveyRequest request) {
		LOG.trace("SurveyRestController.submitSurvey(*)");
		Survey answers = service.submitSurvey(request.getSurveyName(), request.getAnswers(), request.getUserEmail());
		return new SubmitSurveyResponse(answers);
	}

	/**
	 * Gets a list of available technologies for the surveys.
	 *
	 * @return a list of available technologies for the surveys
	 */
	@RequestMapping(value = "/getTechList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GetTechListResponse getTechList() {
		LOG.trace("SurveyRestController.getTechList()");
		List<String> techList = service.getTechList();
		return new GetTechListResponse(techList);
	}

}