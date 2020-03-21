<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<div id="main" class="park-home">

	<c:forEach items="${parks}" var="park">
		<div class="card md-3">
			<div class="row no-gutters">
			<div class="col-md-6">
				<c:url value="/details" var="parkDetailUrl">
					<c:param name="parkCode" value="${park.parkCode}" />
				</c:url>
				<a href="${parkDetailUrl}"> <img class="card-img"
					src="img/parks/${park.imageName}.jpg"
					alt="parkImage${park.imageName}" />
				</a>
			</div>
			<div class="col-md-6">
			<div class="card-body">
				<div>
					<c:url value="/details" var="parkDetailUrl">
						<c:param name="parkCode" value="${park.parkCode}" />
					</c:url>
					<h5>
						<a class="card-title" href="${parkDetailUrl}">
							${park.parkName} </a>
					</h5>
				</div>
				<div class="card-text">${park.parkDescription}</div>
				<a href="${parkDetailUrl}" class="btn btn-primary">Details</a>
			</div>
			</div>
		</div>
		</div>
	</c:forEach>

</div>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />