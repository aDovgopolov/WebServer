var serverHostName = window.location.hostname;

var serverProtocolName = window.location.protocol;

//var portName = window.location.port;
var portName = "8080";
document.write(portName);

if (portName.length == 0) {
    portName = "80";

}

if (serverHostName === "localhost")
{
    serverPath = serverProtocolName + "//" + serverHostName + ":" + portName;
    document.write(serverPath);
}
else
{
    serverPath = serverProtocolName + "//" + serverHostName;
}

function serverConnectFunc(serverUrl, jsonData) {
    $.ajax({
        url: serverUrl + "/webapp/s",
        headers: {  'Access-Control-Allow-Origin': url },
        type: 'POST',
        data: jsonData,
        //header
        dataType: 'jsonp',
        async: true,

        success: function (event) {
            switch (event["answer"])
            {
                case "ok":
                    alert("success");
                    break;

                case "names":
                    alert("try it");
                     var keysList = event["list"];//.toJSON().replace("[", ""). replace("]", "").split(",");

                     $("#table_names").empty();
                    alert(keysList);
                    alert(keysList[0]);
                    alert(keysList['name']);
                     keysList.forEach(function(item, i, arr) {
                         $("#table_names").append("<tr><td>" + item.ldap + "</td>" +
                             "<td>" + item.name + "</td></tr>"+
                             "<td>" + item.sername + "</td></tr>");
                     });

                    break;
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function showAllNames()
{
    var jsonData = new Object();
    jsonData.command = "0";

    alert("T");
    alert(serverPath);
    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}

function addName()
{

    alert("T1");
    var jsonData = new Object();
    jsonData.command = "1";
    jsonData.name = $('#NewNameInput').val();

    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}