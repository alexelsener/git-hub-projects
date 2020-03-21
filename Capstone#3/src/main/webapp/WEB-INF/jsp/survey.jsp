<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<div id="main">

	<div class="park-survey">
		<div>
			<c:url var="surveyResultsUrl" value="/survey" />
			<form:form action="${surveyResultsUrl}" method="POST"
				modelAttribute="surveyData">
				<div>
					<label>Favorite National Park: </label> <form:select name="parkCode" path="parkCode">
						<c:forEach items="${parks}" var="park" >
							<form:option value="${park.parkCode}" >${park.parkName}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<div>
					<label>Your Email: </label> <form:input type="text" name="emailAddress" path="emailAddress" />
					<form:errors path="emailAddress" class="error"/>
				</div>
				<div>
					<label>State of Residence: </label> <form:select name="state" path="state">
						<c:forEach items="${states}" var="state">
							<form:option value="${state.name}">${state.name}</form:option>
						</c:forEach>
					</form:select>
					<form:errors path="stateValid" class="error"/>
				</div>
				<div class="checkbox">
					<label>Activity Level: </label>
					<div>
						<c:forEach items="${activityLevels}" var="activityLevel" >
							<form:radiobutton id="${activityLevel.activityLevel}"
								name="activityLevel" value="${activityLevel.activityLevel}" path="activityLevel"/>
							
							<label for="${activityLevel.activityLevel}">${activityLevel.activityLevel}</label>
						</c:forEach>
					</div>
					<form:errors path="activityLevelValid" class="error"/>
				</div>
				<div>
					<button type="submit">Submit Your Survey</button>
				</div>
			</form:form>
		</div>
	</div>

</div>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />