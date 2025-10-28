<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<link rel="stylesheet" href="<c:url value='/styles/main.css'/>"/>

<main class="sr-main" role="main">
  <h2 class="sr-section-title">Catalogo</h2>

  <c:choose>
    <c:when test="${not empty prodotti}">
      <section class="sr-grid" aria-label="Prodotti">
        <c:forEach var="p" items="${prodotti}">
          <article class="sr-card" aria-label="${p.nome}">
            <a class="sr-card__imgwrap" href="${pageContext.request.contextPath}/prodotto?id=${p.id}">
              <img class="sr-card__img" src="<c:url value='/${p.imageUrl}'/>" alt="${p.nome}" loading="lazy"/>
              <span class="sr-badge">${p.categoria}</span>
              <c:if test="${p.qta <= 0}">
                <span class="sr-badge sr-badge--soldout">Esaurito</span>
              </c:if>
            </a>

            <div class="sr-card__body">
              <h3 class="sr-card__title">${p.nome}</h3>
              <p class="sr-card__desc">${p.descrizione}</p>

              <div class="sr-card__meta">
                <span class="sr-stock">
                  <c:choose>
                    <c:when test="${p.qta <= 0}">Non disponibile</c:when>
                    <c:when test="${p.qta <= 5}">Ultimi ${p.qta} pezzi</c:when>
                    <c:otherwise>Disponibili: ${p.qta}</c:otherwise>
                  </c:choose>
                </span>
                <strong class="sr-price">€ ${p.prezzo}</strong>
              </div>

              <c:choose>
                <c:when test="${p.qta > 0}">
                  <form class="sr-card__actions" method="post" action="<c:url value='/carrello'/>">
                    <input type="hidden" name="action" value="add"/>
                    <input type="hidden" name="productId" value="${p.id}"/>
                    <input type="hidden" name="name" value="${p.nome}"/>
                    <input type="hidden" name="price" value="${p.prezzo}"/>
                    <input class="sr-qty" type="number" name="qty" min="1" max="${p.qta}" value="1" aria-label="Quantità disponibile"/>
                    <button class="sr-btn sr-btn--primary" type="submit">Aggiungi al carrello</button>
                  </form>
                </c:when>
                <c:otherwise>
                  <div class="sr-card__actions">
                    <input class="sr-qty" type="number" value="0" disabled />
                    <button class="sr-btn" type="button" disabled>Esaurito</button>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
          </article>
        </c:forEach>
      </section>
    </c:when>

    <c:otherwise>
      <section>
        <p class="sr-muted">Nessun prodotto trovato. Prova a rimuovere i filtri o a cercare un termine diverso.</p>
        <div class="sr-cats" style="margin-top:.5rem">
          <a class="sr-chip sr-chip--active" href="${pageContext.request.contextPath}/catalogo?cat=tutte">Vedi tutti</a>
        </div>
      </section>
    </c:otherwise>
  </c:choose>
</main>

<%@ include file="/WEB-INF/includes/footer.jspf" %>