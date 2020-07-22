<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/campaigns.ftlh" as campaigns>
<#import "parts/paginator.ftlh" as paginator>

<@template.baseTemplate>

    <@campaigns.campaignsList />
    <@paginator.paginator url page />
    <@template.button "GET" "/addNewCampaign" "Add campaign" />

</@template.baseTemplate>
