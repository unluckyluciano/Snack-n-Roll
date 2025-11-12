<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="<c:url value='/styles/styles.css'/>"/>

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Accedi • Snack'n'Roll</title>
</head>


<body class="auth-page">
<div class="auth-wrap">
  <a class="auth-brand" href="${pageContext.request.contextPath}/">
<img class="sr-logo__img" src="<c:url value='/images/logo.png'/>" alt="Snack’n’Roll"/>
    <h1 class="auth-brand__name">Snack’n’Roll</h1>
  </a>

  <div class="auth-card">
    <h2>Accedi</h2>
    <p class="auth-sub">Bentornato. Inserisci le credenziali per entrare.</p>

    <c:if test="${not empty param.error or not empty requestScope.error}">
      <div class="auth-error">
        <c:out value="${param.error != null ? param.error : requestScope.error}" />
      </div>
    </c:if>

    <form class="auth-form" method="post" action="${pageContext.request.contextPath}/login" autocomplete="on" novalidate>
      <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}" />
      <div class="auth-field">
        <label class="auth-label" for="email">Email</label>
        <input class="auth-input" id="email" name="email" type="email" required placeholder="nome@esempio.it" />
      </div>
      <div class="auth-field auth-pw">
        <label class="auth-label" for="password">Password</label>
        <input class="auth-input" id="password" name="password" type="password" minlength="8" required placeholder="••••••••" />
        <button type="button" class="auth-toggle" onclick="
          const p=document.getElementById('password');
          p.type = p.type==='password' ? 'text' : 'password';
          this.textContent = p.type==='password' ? 'Mostra' : 'Nascondi';
        ">Mostra</button>
      </div>
      <div class="auth-row">
        <button class="auth-btn" type="submit">Accedi</button>
        <a class="auth-help" href='${pageContext.request.contextPath}/register'>Crea un account</a>
      </div>
    </form>
  </div>

  <p class="auth-foot">
    <a href="${pageContext.request.contextPath}/">← Torna alla Home</a>
  </p>
</div>
</body>
</html>