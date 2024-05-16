// define function to update Sleep quality value 
function updateSleepQualityValue(value) {
    document.getElementById('sleepQualityValue').textContent = value; // retrieve text content  in container with ID sleepQualityValue 
}
    document.getElementById('addInterruption').addEventListener('click', function() { // add click event listener to button with ID addInterruption 
        var interruptionsFieldset = document.getElementById('interruptionsFieldset'); // access element with ID interruptionsFieldset
        var interruptionCount = interruptionsFieldset.getElementsByClassName('interruption-entry').length; // count the number of entries in interruptionFieldset 

        // Create new interruption entry
        var newInterruptionEntry = document.createElement('div'); // create a new div for interruption entry 
        newInterruptionEntry.classList.add('interruption-entry'); // add to interruption entry class 
        
        var timeLabel = document.createElement('label'); // create a new label 
        timeLabel.setAttribute('for', 'interruptionTime' + (interruptionCount + 1)); // set the for attribute of timeLabel to bind label to form element, dynamically generate ID 
        timeLabel.textContent = 'Time:'; // assign text "Time" to timelabel 

        var timeInput = document.createElement('input'); // create new input field for time 
        timeInput.setAttribute('type', 'time'); // set type to time to allow users to input in HH MM format
        timeInput.setAttribute('id', 'interruptionTime' + (interruptionCount + 1));  // set ID of input element 
        timeInput.setAttribute('name', 'interruptionTime' + (interruptionCount + 1)); // set name of input element 

        var reasonLabel = document.createElement('label'); // create new label for reason 
        reasonLabel.setAttribute('for', 'interruptionReason' + (interruptionCount + 1)); // set for attribute to associate label with reason input element 
        reasonLabel.textContent = 'Reason:'; // set text Content as "Reason"

        var reasonInput = document.createElement('input'); // create an reason input element 
        reasonInput.setAttribute('type', 'text'); // set type to text 
        reasonInput.setAttribute('id', 'interruptionReason' + (interruptionCount + 1)); // set ID of input element 
        reasonInput.setAttribute('name', 'interruptionReason' + (interruptionCount + 1)); // set name of input element 

        // append new elements to new interruption entry 
        newInterruptionEntry.appendChild(timeLabel); 
        newInterruptionEntry.appendChild(timeInput);
        newInterruptionEntry.appendChild(reasonLabel);
        newInterruptionEntry.appendChild(reasonInput);

        
    });
    function addActivity() { // define function to add acitivities
        var activitiesList = document.getElementById('activitiesList'); // fetch content in ID for activities list 
        var newActivity = document.getElementById('newActivity').value.trim(); // strip whitespace
        if(newActivity) { // if there is any text in the new activities 
            var checkbox = document.createElement('input'); // create a new input field
            checkbox.type = 'checkbox'; // type checkbox 
            checkbox.name = 'activitiesBeforeSleep'; // assign a name 
            checkbox.value = newActivity; // give it a value of the input newActivity 
            
            // Create label for the new checkbox
            var label = document.createElement('label');
            label.textContent = newActivity; // set text content of label to new activity name 
            label.insertBefore(checkbox, label.firstChild); // insert checkbox before first child of the label so that checkbox appears before the text in HTML layout 
            
            // append the new checkbox and label to the list of activities
            activitiesList.appendChild(label);
            
            // clear the input field for new activity
            document.getElementById('newActivity').value = '';
        }
    }

    document.getElementById('addActivityBtn').addEventListener('click', addActivity); // add event listener to button with ID addActivitybutton to trigger addActivity function 

function addInterruptionEntry(time = "", reason = "") { // def function to add interruption entry, empty strings default 

    const interruptionsFieldset = document.getElementById('interruptionsFieldset'); // fetch content in interruptionsFieldset 
        const interruptionEntry = document.createElement('div'); // create a new div for interruption entry 
        interruptionEntry.classList.add('interruption-entry'); // add to class interruption entry 
        interruptionEntry.innerHTML = ` 
            <label>Time:</label>
            <input type="time" value="${time}">
            <label>Reason:</label>
            <input type="text" value="${reason}">
        `; // set inner HTML of entry with values 
        interruptionsFieldset.appendChild(interruptionEntry); // append entry to interruptions fieldset 


}

// Function to add a new activity checkbox dynamically
function addActivityCheckbox(activity) {
    const activitiesList = document.getElementById('activitiesList'); // fetch content at ID activities List 

    const checkbox = document.createElement('input'); // create input element checkbox 
    checkbox.type = 'checkbox'; // type checkbox 
    checkbox.name = 'activitiesBeforeSleep'; // assign name 
    checkbox.value = activity; // value is the activity parameter
    checkbox.checked = true; //auto marks the checkbox true when it it's created 

    const label = document.createElement('label'); // create a label element 
    label.textContent = activity; // set text content of label to be the activity 
    label.insertBefore(checkbox, label.firstChild); // insert checkbox into the label element before the label's first child 

    activitiesList.appendChild(label); // append new label with checkbox to the activities list element 
}



document.addEventListener("DOMContentLoaded", function() { //run this function when the DOM content is loaded 
    function getQueryParams() { // define function to get query parameters 
        const params = {}; // initialize a empty set  for parameters 
        window.location.search.substring(1).split("&").forEach(function(part) { // parse the query string of the URL, remove substring(1) which is ?, split on &, get substring of key=value 
            const item = part.split("="); // split key=value on = 
            params[item[0]] = decodeURIComponent(item[1]); // key = item[0], decode any URI components int he value = item[1]
        });
        return params; // return kv pairs 
    }

    function fetchSleepData(date) { // function to fetch sleep Data by date 
        fetch(`http://localhost:8000/api/fetchSleepDataByDate/${date}`) // call the API endpoint 
            .then(response => response.json()) // process response from server as a JSON object 
            .then(data => {
                populateForm(data); // call populateForm function with data in it to populate form 
            })
            .catch(error => console.error('Error fetching sleep data:', error)); // catch and throw any errors 
    }

    function populateForm(data) { // define function to populate form for when we want to go back and make updates to the log 
        document.getElementById('date').value = data.date.S; // fetch content at ID date 
           document.getElementById('sleepStart').value = data.sleepStart.S; // fetch sleepStart time 
           document.getElementById('sleepEnd').value = data.sleepEnd.S; // fetch sleep end time 
           document.getElementById('sleepQuality').value = data.quality.N; // fetch sleep quality  
           document.getElementById('sleepQualityValue').textContent = data.quality.N; // fetch sleep quality number 

           // Set caffeine and alcohol intake
           document.getElementById('caffeineYes').checked = data.caffeineIntake.BOOL;
           document.getElementById('caffeineNo').checked = !data.caffeineIntake.BOOL;
           document.getElementById('alcoholYes').checked = data.alcoholIntake.BOOL;
           document.getElementById('alcoholNo').checked = !data.alcoholIntake.BOOL;

           // Set environmental factors
           document.getElementById('lightLevel').value = data.environmentalFactors.M.lightLevel.S;
           document.getElementById('noiseLevel').value = data.environmentalFactors.M.noiseLevel.S;
           document.getElementById('temperature').value = data.environmentalFactors.M.temperature.S;


           // Clear existing interruptions first
               const interruptionsFieldset = document.getElementById('interruptionsFieldset'); // grab the content at interruptionsFieldset ID 

               interruptionsFieldset.innerHTML = ''; // Clears all content including any old interruptions

               // iterate over each interruption
               data.interruptions.L.forEach((interruption, index) => {
                   addInterruptionEntry(interruption.M.time.S, interruption.M.type.S); // add time and type to interruption entry 
               });

          // Clear existing dynamically added activities
             document.querySelectorAll('input[name="activitiesBeforeSleep"]:not(:checked)').forEach(input => { // select all activities before sleep 
                 input.parentElement.remove();
             });

             // Check existing and add new activity checkboxes as needed
             data.activitiesBeforeSleep.SS.forEach(activity => { // iterate over each activity 
                 let existingCheckbox = document.querySelector(`input[name="activitiesBeforeSleep"][value="${activity}"]`); // grab the existing activities that have a checkbox 
                 if (!existingCheckbox) { // if there is no checkbox 
                     addActivityCheckbox(activity); // add checkbox for the activity 
                 } else {
                     existingCheckbox.checked = true; // mark it true 
                 }
             });


    }
    
    document.getElementById('addInterruption').addEventListener('click', function() { // add listener for click on addInterruption button 
        addInterruptionEntry(); // call add Interruption function to execute 

        
        const interruptionsFieldset = document.getElementById('interruptionsFieldset'); // Move the add Interruption button to the end of the fieldset
        interruptionsFieldset.appendChild(this); // append to interruptionFieldset 
    });


    function updateSleepData(e) { // define function to update the sleepData 
        e.preventDefault(); // prevent Default action of sending POST method 
        const formData = { // create a form object with updated values 
            date: document.getElementById('date').value,
            sleepStart: document.getElementById('sleepStart').value,
            sleepEnd: document.getElementById('sleepEnd').value,
            quality: document.getElementById('sleepQuality').value,
            caffeineIntake: document.querySelector('input[name="caffeineIntake"]:checked').value === '1', 
            alcoholIntake: document.querySelector('input[name="alcoholIntake"]:checked').value === '1', 
            environmentalFactors: {
                    lightLevel: document.getElementById('lightLevel').value,
                    noiseLevel: document.getElementById('noiseLevel').value,
                    temperature: document.getElementById('temperature').value
                },
            interruptions: collectInterruptions(), // call collectinterruptions function 
            activitiesBeforeSleep: collectActivities() // call collect Activities function 
        };

        const queryParams = getQueryParams(); // call getQueryParams functions 
        const date = queryParams['date']; // extrapolate the date from the parameters

        fetch(`http://localhost:8000/api/updateSleepData/${date}`, { // call the API endpoint to update the sleep data 
            method: "PUT", // use PUT method 
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData) // turn form object into JSON string object 
        })
        .then(response => response.json()) // process response from server as JSON object 
        .then(data => {
            console.log(data); // return the data in the console just to be sure 
            alert("Sleep data updated successfully!");
    
        })
        .catch(error => { // catch and throw any errors 
            console.error('Error:', error);
            alert("Failed to update sleep log.");
        });
    }

    const queryParams = getQueryParams(); // intialize query Params by calling this function 
    if (queryParams['date']) { // if there is a date in query Params
        fetchSleepData(queryParams['date']); // fetch the sleepData on this date 
    }

    const form = document.getElementById("sleepLogForm"); // access the content from sleepLog Form 
    form.addEventListener("submit", updateSleepData); // listen for the submit button to execute updatesleepdata 
});


function collectInterruptions() { // define collect interruptions function 
    const interruptions = []; // initialize an empty array 
    // Access each interruption entry within the container
    document.querySelectorAll('#interruptionsFieldset .interruption-entry').forEach(entry => { // iterate over each entry in the interruption fieldset container 
        const timeInput = entry.querySelector('input[type="time"]'); // grab the time input 
        const reasonInput = entry.querySelector('input[type="text"]'); // grab the type input 
        // Check if both time and reason inputs are found and have values
        if (timeInput && reasonInput && timeInput.value && reasonInput.value) {
            interruptions.push({ // append to the interruptions array 
                time: timeInput.value,
                type: reasonInput.value
            });
        }
    });
    return interruptions; // return the array 
}

function collectActivities() { // define collect activities function 
    const activities = []; // initialize empty array 
    document.querySelectorAll('input[name="activitiesBeforeSleep"]:checked').forEach(checkbox => { // iterate over each activity with checkbox 
        activities.push(checkbox.value); // append to list of activities 
    });
    return activities; // return the array of activities 
}




