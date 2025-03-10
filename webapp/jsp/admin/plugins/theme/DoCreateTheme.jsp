<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="themeTheme" scope="session" class="fr.paris.lutece.plugins.theme.web.ThemeJspBean" />

<%
	themeTheme.init( request, fr.paris.lutece.plugins.theme.web.ThemeJspBean.RIGHT_MANAGE_THEMES );
    response.sendRedirect( themeTheme.doCreateTheme( request ) );
%>

