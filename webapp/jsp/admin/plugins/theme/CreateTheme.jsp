<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="themeTheme" scope="session" class="fr.paris.lutece.plugins.theme.web.ThemeJspBean" />

<% themeTheme.init( request, fr.paris.lutece.plugins.theme.web.ThemeJspBean.RIGHT_MANAGE_THEMES ); %>
<%= themeTheme.getCreateTheme( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
