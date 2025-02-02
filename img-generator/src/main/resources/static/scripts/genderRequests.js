const BASE_URL_IMG= window.location.protocol +'//'+ window.location.host + '/img_generator'
const PHOTO_QUERY = BASE_URL_IMG + "/photo-query"

async function sendFemaleReq()
{
    var url = PHOTO_QUERY + "/edit/female"
    var queries = document.getElementById("female_requests").value.split('\n')
    sendRequest(url, queries)
}

async function sendMaleReq()
{
    var url = PHOTO_QUERY + "/edit/male"
    var queries = document.getElementById("male_requests").value.split('\n')
    sendRequest(url, queries)
}


async function sendRequest(url,queries)
{
    const sendData = queries.map(item => ({ title: item }));
    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(sendData)
            });
    }catch(e)
    {
         console.log(e);
    }
    console.log(response);
}