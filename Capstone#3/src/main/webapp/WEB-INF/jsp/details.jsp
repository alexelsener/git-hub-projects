<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<div id="main">

	<div class="park-detail">
		<h2>${park.parkName}</h2>
		<div>
			<img src="img/parks/${park.imageName}.jpg"
				alt="parkImage${park.imageName}" />
		</div>
		<div>
			<div>${park.parkDescription}</div>
			<div>State: ${park.state}</div>
			<div>Acres: ${park.acreage}</div>
			<div>Elevation: ${park.elevationInFeet}</div>
			<div>Trail Miles: ${park.milesOfTrail}</div>
			<div>Campsites: ${park.numberOfCampsites}</div>
			<div>Climate: ${park.climate}</div>
			<div>Founded: ${park.yearFounded}</div>
			<div>Visitors per year: ${park.annualVisitorCount}</div>
			<div>Quote: ${park.inspirationalQuote}</div>
			<div>Source: ${park.inspirationalQuoteSource}</div>
			<div>Fee: ${park.entryFee}</div>
			<div>Number of species: ${park.numberOfAnimalSpecies}</div>
		</div>
	</div>
</div>

<div>
	<c:url var="parkDetailsUrl" value="/details">
		<c:param name="parkCode" value="${park.parkCode}" />
	</c:url>
	<form action="${parkDetailsUrl}" method="POST">
		<label for="tempSelect">Temperature Preference (Celsius /
			Fahrenheit)</label> <select name="tempSelect" id="tempSelect">
			<option value="c">Celsius</option>
			<option value="f">Fahrenheit</option>
		</select>
		<div>
		<button type="submit" class="btn btn-primary">Change Temperature Measurement</button>
		</div>
	</form>
</div>

<div id="weather-layout" class="weather-forecast card-group">
	<c:forEach items="${forecasts}" var="forecast">
		<div class="card">
			<div>
				<img src="img/weather/${forecast.imageName}.png" />
			</div>
			<div>High: 
			<c:choose><c:when test="${forecast.celsius == false}"> ${forecast.high} &deg;F</c:when>
			<c:when test="${forecast.celsius == true}"> ${forecast.celsiusHigh} &deg;C</c:when>
			</c:choose>
			</div>
			<div>Low: 
			<c:choose><c:when test="${forecast.celsius == false}">${forecast.low} &deg;F</c:when>
			<c:when test="${forecast.celsius == true}">${forecast.celsiusLow} &deg;C</c:when>
			</c:choose>
			</div>
			<div>${forecast.summary}</div>
			<div>
				<ul>
					<c:forEach items="${forecast.recommendations}" var="recommendation">
						<li>${recommendation}</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</c:forEach>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />