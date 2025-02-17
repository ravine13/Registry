<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Phone Directory Web App</title>
    <!-- Include jQuery from a CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function(){

            // Individual Contact Search
            $("#searchIndividualBtn").click(function(){
                // Retrieve input values
                var phoneHash = $("#phoneHash").val().trim();
                var maskedName = $("#maskedName").val().trim();
                var maskedPhone = $("#maskedPhone").val().trim();

                // Determine the API URL based on the provided search criteria
                var apiUrl = "http://localhost:8085/ContactRegistry2_war_exploded/contacts/search?";
                if (phoneHash !== "") {
                    apiUrl += "phoneHash=" + encodeURIComponent(phoneHash);
                } else if (maskedName !== "" && maskedPhone !== "") {
                    apiUrl += "maskedName=" + encodeURIComponent(maskedName) + "&maskedPhone=" + encodeURIComponent(maskedPhone);
                } else {
                    $("#individualResult").html("<p>Please enter either a phone hash or both masked name and masked phone.</p>");
                    return;
                }

                // Make the AJAX GET call to search for an individual contact
                $.ajax({
                    url: apiUrl,
                    method: "GET",
                    dataType: "json",
                    success: function(data){
                        if (data.length === 0) {
                            $("#individualResult").html("<p>No matching contact found.</p>");
                        } else {
                            var resultHtml = "<table border='1' cellspacing='0' cellpadding='5'><tr>" +
                                "<th>ID</th><th>Full Name</th><th>Email</th><th>Masked Phone</th></tr>";
                            $.each(data, function(i, contact){
                                resultHtml += "<tr>" +
                                    "<td>" + contact.id + "</td>" +
                                    "<td>" + contact.fullName + "</td>" +
                                    "<td>" + contact.email + "</td>" +
                                    "<td>" + contact.phoneNumber + "</td>" +
                                    "</tr>";
                            });
                            resultHtml += "</table>";
                            $("#individualResult").html(resultHtml);
                        }
                    },
                    error: function(xhr, status, error){
                        $("#individualResult").html("<p>Error fetching contact: " + error + "</p>");
                    }
                });
            });

            // Organization Contacts Search
            $("#searchOrgBtn").click(function(){
                var organization = $("#orgName").val().trim();
                if(organization === ""){
                    $("#orgResult").html("<p>Please enter an organization name.</p>");
                    return;
                }

                // Construct the API URL for organization search
                var apiUrl = "http://localhost:8085/ContactRegistry2_war_exploded/contacts/organization/" + encodeURIComponent(organization);

                // Make the AJAX GET call to retrieve contacts for the organization
                $.ajax({
                    url: apiUrl,
                    method: "GET",
                    dataType: "json",
                    success: function(data){
                        if(data.length === 0){
                            $("#orgResult").html("<p>No contacts found for organization: " + organization + ".</p>");
                        } else {
                            var resultHtml = "<table border='1' cellspacing='0' cellpadding='5'><tr>" +
                                "<th>ID</th><th>Full Name</th><th>Email</th><th>Masked Phone</th></tr>";
                            $.each(data, function(i, contact){
                                resultHtml += "<tr>" +
                                    "<td>" + contact.id + "</td>" +
                                    "<td>" + contact.fullName + "</td>" +
                                    "<td>" + contact.email + "</td>" +
                                    "<td>" + contact.phoneNumber + "</td>" +
                                    "</tr>";
                            });
                            resultHtml += "</table>";
                            $("#orgResult").html(resultHtml);
                        }
                    },
                    error: function(xhr, status, error){
                        console.log("error",error)
                        console.log("xhr",xhr)
                        console.log("status",status)
                        $("#orgResult").html("<p>Error fetching contacts: " + error + "</p>");
                    }
                });
            });
        });
    </script>
</head>
<body>
<h1>Phone Directory Web App</h1>

<!-- Section 1: Individual Contact Search -->
<h2>Search Individual Contact</h2>
<p>
    Search by entering <strong>either</strong> a phone number hash <strong>or</strong> both masked name and masked phone.
</p>
<div>
    <label>Phone Hash:</label>
    <input type="text" id="phoneHash" placeholder="Enter phone hash" /><br/><br/>
    <label>Masked Name:</label>
    <input type="text" id="maskedName" placeholder="Enter masked name" /><br/><br/>
    <label>Masked Phone:</label>
    <input type="text" id="maskedPhone" placeholder="Enter masked phone" /><br/><br/>
    <button id="searchIndividualBtn">Search Individual Contact</button>
</div>
<div id="individualResult" style="margin-top:20px;"></div>

<hr/>

<!-- Section 2: Organization Contacts Search -->
<h2>Search Contacts by Organization</h2>
<div>
    <label>Organization Name:</label>
    <input type="text" id="orgName" placeholder="Enter organization name" />
    <button id="searchOrgBtn">Search Organization Contacts</button>
</div>
<div id="orgResult" style="margin-top:20px;"></div>
</body>
</html>
