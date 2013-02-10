<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
<c:if test="${info != null}">
    <div id="info" class="fixed-msg">
        <div class="alert alert-success">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <strong>Success.</strong> ${info}
        </div>
    </div>
    <script type="text/javascript">
        (function($) {
            $(document).ready(function() {
                $('#info').fadeOut(0).fadeIn(800).delay(8000).fadeOut();
                $('#info a.close').click(function(){
                    $('#info').hide();
                })
            });
        })(jQuery);
    </script>
</c:if>

<c:if test="${errors != null}">
    <div id="errors">
        <div class="alert alert-error">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <strong>Errors have occurred.</strong>
            <c:forEach items="${errors}" var="error">${error}</c:forEach>
        </div>
    </div>
    <script type="text/javascript">
        (function($) {
            $(document).ready(function() {
                $('#errors a.close').click(function(){
                    $('#errors').fadeOut();
                });
            });
        })(jQuery);
    </script>
</c:if>
</div>
