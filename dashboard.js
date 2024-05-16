// jQuery read function
$(document).ready(function() {  // ensure that function runs only after entire HTML page and DOM elements has been loaded
    $('[data-toggle="collapse"]').on('click', function() { // listen for click on collapse attributed
        var $arrow = $(this).find(".arrow");
        // target the child elements within the .arrow class and store in $arrow
        var target = $(this).attr('href');
        // retrieve href attribute from clicked element
        // check if the target collapse is currently shown or hidden
        $(target).on('shown.bs.collapse', function () {
            $arrow.html('&#9660;'); // change to downward arrow when shown
        }).on('hidden.bs.collapse', function () {
            $arrow.html('&#9654;'); // change to rightward arrow when hidden
        });
    });
});

fetch('http://localhost:8000/api/fetchNights') // call API endpoint to get night data 
    .then(response => response.json()) // process response and convert it to json
    .then(nights => { 
    const container = document.querySelector('.dashboard-container'); // select HTML element with .dashboard-container class 
    container.innerHTML = ''; //  clear existing content in dashboard-container to prepare for dynamic population of dashboard

    nights.sort((a, b) => b.date.S.localeCompare(a.date.S)); // sort by  descending order, localeCompare to compare dates (strings) 

    nights.forEach((night, index) => { // iterate over each night
        console.log(nights);// return night in console for double checking 
        const nightSection = document.createElement('div'); // create a new nighsection div in dashboard.html 
        nightSection.className = 'nightSection'; // assign it a class for styling
        nightSection.id = `night${index+1}Section`; // assign each new night an id 

        const formattedInterruptions = night.interruptions && night.interruptions.L ? night.interruptions.L.map(interrupt => ({ // check if night.interruptions exists and is a list (L), then map over each item 
                type: interrupt.M.type.S,  // extract the type from each interruption
                time: interrupt.M.time.S    // extract the time from each interruption
            })) : []; // if the interruption doesn't exist or contain a list, default to an empty array 

            const interruptionsHTML = formattedInterruptions.map(interrupt => // transform each interruption object into an HMTL <p> 
                `<p>${interrupt.type} at <strong>${interrupt.time}</strong></p>` //  dynamically insert interryption type and time(strong tags for bold font) into HTML 
            ).join(''); // concatencate all HMTL strings <p> into onle long HTML string without any separator

        // check if activitiesBeforeSleep is in the expected string set format 
        const activitiesBeforeSleep = night.activitiesBeforeSleep && night.activitiesBeforeSleep.SS 
                                    ? night.activitiesBeforeSleep.SS 
                                    : []; // Fallback to empty array if not present or in unexpected format
        // map function to convert each string in the activitiesBeforeSleep array to an HMTL <li> element 
        const activitiesHTML = activitiesBeforeSleep.map(activity => 
        `<li>${activity}</li>` //convert to <li> element 
        ).join(''); // concatenate to HMTL string 

        const dateDisplay = night.date.S || "Unknown Date";  // fetch date or elese put "Unknown data "
        const sleepStartDisplay = night.sleepStart.S || "Unknown Start Time"; // fetch the sleep start or replace with unknown 
        const sleepEndDisplay = night.sleepEnd.S || "Unknown End Time"; // fetch the end time or replace with unknown 
        const lightLevel = night.environmentalFactors.M.lightLevel.S|| "Unknown"; // fetch light level or replace with unknown 

    const deleteButtonHTML = `<button onclick="deleteSleepLog('${night.date.S}')" class="btn btn-danger btn-sm m-2">Delete</button>`; // create deleteButton for each night date card and tie it to deleteSleepLog

    const updateButtonHTML = `<a href="update.html?date=${encodeURIComponent(night.date.S)}" class="btn btn-primary btn-sm m-2">Update</a>`; // create Update button for each night/date card and tie it to deleteSleeplog 

        // Construct the inner HTML for each night
        let innerHTML = `
        <h2 class="data-title" data-toggle="collapse" href="#night${index+1}Details" aria-expanded="false" aria-controls="night${index+1}Details">
            ${dateDisplay} <span class="arrow">&#9654;</span>
        </h2>
        <div class="collapse" id="night${index+1}Details">
            <div class="data-point">
                <h2 class="data-title">Sleep Quality</h2>
                <p>Last Night: <strong>${night.quality.N}/5</strong></p>
            </div>
            <div class="data-point">
                <h2 class="data-title">Sleep Start and End Time</h2>
                <p>Sleep Start: <strong>${sleepStartDisplay}</strong>, Sleep End: <strong>${sleepEndDisplay}</strong></p>
            </div>
            <div class="data-point">
                <h2 class="data-title">Interruptions</h2>
                ${interruptionsHTML} 

            </div>
            <div class="data-point">
                <h2 class="data-title">Caffeine & Alcohol Intake</h2>
                <p>Caffeine: <strong>${night.caffeineIntake.BOOL ? 'Yes' : 'No'}</strong>, Alcohol: <strong>${night.alcoholIntake.BOOL ? 'Yes' : 'No'}</strong></p>
            </div>
            <div class="data-point">
                <h2 class="data-title">Activities Before Sleep</h2>
                <ul>${activitiesHTML}</ul>
            </div>
            <div class="data-point">
                <h2 class="data-title">Environmental Factors</h2>
                <p>Light Level: <strong>${night.environmentalFactors.M.lightLevel.S}</strong>, Noise Level: <strong>${night.environmentalFactors.M.noiseLevel.S}</strong>, Temperature: <strong>${night.environmentalFactors.M.temperature.S}</strong>°C</p>
            </div>
        </div> 
        ${deleteButtonHTML}
        ${updateButtonHTML}
        `;

        nightSection.innerHTML = innerHTML; // udpate innerHTML with injection
        container.appendChild(nightSection); // append each night to the nightSection 
    });

    // re-initialize components if necessary, e.g., Bootstrap collapse
    $('.collapse').collapse();
    })
    .catch(error => console.error('Error fetching nights:', error)); // catch adn throw error



function deleteSleepLog(date) { // call the deleteSleep log function 
    fetch(`http://localhost:8000/api/deleteSleepData/${date}`, { // take parameter date as unique identifier 
        method: "DELETE"
    }) // send DELETE request to serve 
    .then(response => { // handle server response 
        if (response.ok) { // if response is successful 200 ok 
            alert("Sleep log deleted successfully!"); // confirm 
            window.location.reload(); // reload the page to refresh the data
        } else { // else throw error 
            alert("Failed to delete sleep log.");
        }
    })
    .catch(error => { // catch and throw error 
        console.error('Error:', error);
        alert("Failed to delete sleep log.");
    });
}