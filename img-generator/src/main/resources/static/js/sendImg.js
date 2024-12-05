const BASE_URL = window.location.protocol +'//'+ window.location.host + '/img_generator'
const IMG_DTO_URL = BASE_URL + "/img"


async function sendFemale()
{
    fileName = getImgName()
    var url = IMG_DTO_URL + "/get/accept?gender=female" + "&imgName="+fileName
    sendToCSS(url)
}

async function sendMale()
{
    fileName = getImgName()
    var url = IMG_DTO_URL + "/get/accept?gender=male" + "&imgName="+fileName
    sendToCSS(url)
}

function randomIntFromInterval(min, max) { 
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

async function getRandImgName()
{
    const url = IMG_DTO_URL+'/get/rand-img-name'
    try
    {
        const response = await fetch
            (url, {
                method: "GET",
                
            });
        var img = await response.text()
        console.log(img)
        return img
    }
    catch(e) 
    {
        console.log(e)
    }
}

function getImgName()
{
    return document.getElementById("hidden-img-name").innerText
}

async function setImg()
{

    var img = await getRandImgName()
    const url = IMG_DTO_URL+'/get/by-name/' + img
    document.getElementById("hidden-img-name").innerHTML = img;
    
    console.log(url)
    try
    {
        const response = await fetch
            (url, {
                method: "GET",
            });

        console.log()    
        const imageBlob = await response.blob()    
        const imageObjectURL = URL.createObjectURL(imageBlob)
        const image = document.getElementById('get_img')
        image.src = imageObjectURL
    }
    catch(e) 
    {
        console.log(e)
    }
}

async function sendToCSS(url)
{
    console.log(url)
    try
    {
        const response = await fetch
            (url, {
                method: "GET",
                
            });
        location.reload();    
    }
    catch(e) 
    {
        console.log(e)
    }
}

async function deleteImg()
{
    fileName =getImgName()
    
    const url = IMG_DTO_URL+'/delete/' + fileName
    console.log(url)
    try
    {
        const response = await fetch
            (url, {
                method: "DELETE",
               
            });
        location.reload();    
    }
    catch(e) 
    {
        console.log(e)
    }
}