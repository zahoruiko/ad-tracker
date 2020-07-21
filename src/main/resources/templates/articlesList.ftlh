<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/articles.ftlh" as articles>
<#import "parts/paginator.ftlh" as paginator>

<@template.baseTemplate>
    
    <@articles.articlesList />
    <@paginator.paginator url page />    
    <@template.button "GET" "/admin/addArticle" "Add article" />
    
</@template.baseTemplate>
