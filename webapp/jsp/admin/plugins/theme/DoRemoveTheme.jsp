<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="theme" scope="session" class="fr.paris.lutece.plugins.theme.web.ThemeJspBean" />

<% 
	theme.init( request , fr.paris.lutece.plugins.theme.web.ThemeJspBean.RIGHT_MANAGE_THEMES );
	response.sendRedirect( theme.doRemoveTheme( request ) );
%>
