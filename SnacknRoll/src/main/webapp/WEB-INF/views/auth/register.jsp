<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Registrati • Snack'n'Roll</title>
  <link rel="stylesheet" href="<c:url value='/styles/styles.css'/>"/>
</head>
<body class="auth-page">
  <form class="auth-form" method="post" action="${pageContext.request.contextPath}/register" autocomplete="on" novalidate>
  <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}" />
  <div class="auth-grid">
    <div class="auth-field">
      <label class="auth-label" for="nome">Nome</label>
      <input class="auth-input" id="nome" name="nome" required placeholder="Nome" />
    </div>
    <div class="auth-field">
      <label class="auth-label" for="cognome">Cognome</label>
      <input class="auth-input" id="cognome" name="cognome" required placeholder="Cognome" />
    </div>
  </div>
  <div class="auth-field">
    <label class="auth-label" for="email">Email</label>
    <input class="auth-input" id="email" name="email" type="email" required placeholder="nome@esempio.it" />
  </div>
  <div class="auth-grid">
    <div class="auth-field auth-pw">
      <label class="auth-label" for="password">Password (min 8)</label>
      <input class="auth-input" id="password" name="password" type="password" minlength="8" required placeholder="••••••••" />
      <button type="button" class="auth-toggle" onclick="
        const p=document.getElementById('password');
        p.type = p.type==='password' ? 'text' : 'password';
        this.textContent = p.type==='password' ? 'Mostra' : 'Nascondi';
      ">Mostra</button>
    </div>
    <div class="auth-field">
      <label class="auth-label" for="indirizzo">Indirizzo spedizione (opz.)</label>
      <input class="auth-input" id="indirizzo" name="indirizzo_spedizione" placeholder="Via e numero, città" />
    </div>
  </div>
  <div class="auth-row">
    <button class="auth-btn" type="submit">Registrati</button>
    <a class="auth-help" href='${pageContext.request.contextPath}/login'>Hai già un account? Accedi</a>
  </div>
</form>
</body>
</html>