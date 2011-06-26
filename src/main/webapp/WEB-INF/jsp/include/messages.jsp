<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${info != null}">
    <div id="info">${info}</div>
    <script type="text/javascript">
        (function($) {
            $(document).ready(function() {
                $('#info').fadeOut(0).fadeIn(800).delay(10000).fadeOut();
            });
        })(jQuery);
    </script>
</c:if>
<c:if test="${errors != null}">
    <div id="errors"><h2>Errors have occurred</h2>
        <ul>
            <c:forEach items="${errors}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
        <div>
            <a href="#" class="close">Close</a>
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
