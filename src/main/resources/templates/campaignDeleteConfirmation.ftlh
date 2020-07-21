<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/authorisation.ftlh" as authorisation>
<#import "parts/menu.ftlh" as menu>

<@template.baseTemplate>

    <div><h3>Campaign delete:</h3></div>
    <div class="mt-5 mb-5"><h5>Are you sure?</h5></div>
    <table border="0">
        <tr>
            <td>
                <@template.button "GET" deleteCampaignUrl "Delete" />
            </td>
            <td>
                <@template.button "GET" "/campaignsList" "Cancel" />
            </td>
        </tr>
    </table>
    
</@template.baseTemplate>