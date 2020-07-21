<#function max x y>
    <#if (x gt y)><#return x><#else><#return y></#if>
</#function>

<#function min x y>
    <#if (x lt y)><#return x><#else><#return y></#if>
</#function>

<#macro paginator url page>
    <#assign
        itemsPerPageAmount = page.getSize()
        currentPage = page.getNumber()
        showNearCurrentPage = 2
        lastPage = page.getTotalPages() - 1
    >
    <#if lastPage lt 0>
        <#assign lastPage = 0>
    </#if>
    <#if (currentPage lte showNearCurrentPage)> <#-- currentPage among first 5 pages -->
        <#assign interval = 0..(min((showNearCurrentPage * 2), lastPage))>
    <#elseif ((lastPage - currentPage) lt (showNearCurrentPage + 1))> <#-- currentPage among last 5 pages -->
        <#assign interval = (max(0, (lastPage - (showNearCurrentPage * 2))))..lastPage >
    <#else>
        <#assign interval = (currentPage - showNearCurrentPage)..(currentPage + showNearCurrentPage)>
    </#if>
    
    <div>
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Pages</a>
            </li>
            <#if !(interval?seq_contains(0))>
                <li class="page-item disabled">
                    <a class="page-link" href="${url}?page=0&size=${itemsPerPageAmount}" tabindex="-1">1</a>
                </li>
                <#if currentPage gt (showNearCurrentPage + 1)>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                </#if>
            </#if>
            <#list interval as p>
                <#if p = currentPage>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p+1}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${p}&size=${itemsPerPageAmount}" tabindex="-1">${p+1}</a>
                    </li>
                </#if>
            </#list>                 
            <#if !(interval?seq_contains(lastPage))> 
                <#if (lastPage - currentPage) gt (showNearCurrentPage + 1)>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                </#if>
                
                <li class="page-item">
                    <a class="page-link" href="${url}?page=${lastPage}&size=${itemsPerPageAmount}" tabindex="-1">${lastPage+1}</a>
                </li>
            </#if>
        </ul>
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Strings pro page</a>
            </li>
            <#list [10, 20, 50, 100] as itemsAmount>
                <#if itemsAmount == itemsPerPageAmount>
                    <li class="page-item active">
                        <a class="page-link" href="${url}?page=0&size=${itemsAmount}">${itemsAmount}</a>
                    </li>                
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=0&size=${itemsAmount}">${itemsAmount}</a>
                    </li>                
                </#if>
            </#list>
        </ul>
    </div>
</#macro>