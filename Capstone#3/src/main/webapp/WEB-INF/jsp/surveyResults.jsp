<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<div id="favorites-main" class="d-flex justify-content-center">

	<div>
		<c:forEach items="${favoriteParks}" var="favoritePark">
			<div class="card md-3 favorites-card">
				<div class="card-title">
				Park Name: ${favoritePark.parkName}
				</div>
				<div>State of Park: ${favoritePark.state}</div>
				<div>Number of Votes: ${favoritePark.favoriteCount}</div>
			</div>
		</c:forEach>
	</div>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />