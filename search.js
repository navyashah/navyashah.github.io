// define function to fetch data by a specific date, for searching 
function fetchDataByDate() {
            const date = document.getElementById('searchDate').value; // retrieve value of the input element with ID searchDate
            if (date) { // if there is a date that matches the input search date 
                fetch(`http://localhost:8000/api/fetchSleepDataByDate/${date}`) // call this api endpoint to fetch by sleep date
                    .then(response => { 
                        if (!response.ok) { // throw error 
                            throw new Error('No data found for this date.');
                        }
                        return response.json(); // else return response as a JSON object 
                    })
                    .then(data => { // then display the results 
                        if (data) {
                            displayResults(data);
                        } else { // else handle no data scenario 
                            displayNoDataFound(); 
                        }
                    })
                    .catch(error => { // catch and throw any errors 
                        console.error('Error fetching data:', error);
                        displayNoDataFound(); // display no data message when error
                    });
                    
            } else { // if no date, then prompt to select a date to search 
                alert('Please select a date to search.');
            }
        }

        function displayNoDataFound() { // define function to handle when no date is found 
            const container = document.getElementById('resultsContainer'); // access DOM element where results are displayed 
            container.innerHTML = '<p class="alert alert-warning">No data found for this date. The night is not in the database.</p>'; // display message in innerHTML 
        }

        function displayResults(night) { // define function to displayResults for a night 
            const container = document.getElementById('resultsContainer'); // fetch container element
            container.innerHTML = ''; // clear previous results

            if (!night || Object.keys(night).length === 0) { // if night object is empty, display a message 
                   container.innerHTML = '<p class="alert alert-warning">No data found for this date. The night is not in the database.</p>';
                   return;
            }

            const nightSection = document.createElement('div'); // create a new nightSection div
            nightSection.className = 'nightSection'; // assign a classname 

            //format interruptions 
            const formattedInterruptions = night.interruptions && night.interruptions.L ? night.interruptions.L.map(interrupt =>  
                `<p>${interrupt.M.type.S} at <strong>${interrupt.M.time.S}</strong></p>` 
            ).join('') : 'No interruptions';

            // Formatting activities
            const activitiesHTML = night.activitiesBeforeSleep && night.activitiesBeforeSleep.SS 
                ? night.activitiesBeforeSleep.SS.map(activity => `<li>${activity}</li>`).join('') 
                : 'No activities';

            // construct an innerHTML for an entire night data card 
            const innerHTML = `
                    <h2 class="data-title">${night.date.S}</h2>
                    <div class="data-point">
                        <strong>Sleep Quality:</strong> ${night.quality.N}/5
                    </div>
                    <div class="data-point">
                        <strong>Sleep Start and End Time:</strong>
                        <p>Sleep Start: <strong>${night.sleepStart.S}</strong></p>
                        <p>Sleep End: <strong>${night.sleepEnd.S}</strong></p>
                    </div>
                    <div class="data-point">
                        <strong>Interruptions:</strong>
                        ${formattedInterruptions}
                    </div>
                    <div class="data-point">
                        <strong>Caffeine & Alcohol Intake:</strong>
                        <p>Caffeine: <strong>${night.caffeineIntake.BOOL ? 'Yes' : 'No'}</strong></p>
                        <p>Alcohol: <strong>${night.alcoholIntake.BOOL ? 'Yes' : 'No'}</strong></p>
                    </div>
                    <div class="data-point">
                        <strong>Activities Before Sleep:</strong>
                        <ul>${activitiesHTML}</ul>
                    </div>
                    <div class="data-point">
                        <strong>Environmental Factors:</strong>
                        <p>Light Level: <strong>${night.environmentalFactors.M.lightLevel.S}</strong></p>
                        <p>Noise Level: <strong>${night.environmentalFactors.M.noiseLevel.S}</strong></p>
                        <p>Temperature: <strong>${night.environmentalFactors.M.temperature.S}</strong></p>
                    </div>
                `;

            nightSection.innerHTML = innerHTML; // set innterHTML for a night section card 
            container.appendChild(nightSection); // append to container 
        }
