import requests
from bs4 import BeautifulSoup
import pandas as pd

# Job search parameters
job_title = "python developer"
location = "chennai"
base_url = "https://www.indeed.com/jobs"

# Construct query parameters
params = {
    "q": job_title,
    "l": location,
    "start": 0
}

# Set headers to mimic a browser
headers = {
    "User-Agent": "Mozilla/5.0"
}

job_list = []

# Scrape first 2 pages (each page has 10-15 jobs)
for page in range(0, 20, 10):
    params["start"] = page
    response = requests.get(base_url, headers=headers, params=params)
    soup = BeautifulSoup(response.text, 'html.parser')

    jobs = soup.find_all('div', class_='job_seen_beacon')

    for job in jobs:
        title = job.find('h2', class_='jobTitle')
        company = job.find('span', class_='companyName')
        location = job.find('div', class_='companyLocation')
        summary = job.find('div', class_='job-snippet')

        job_data = {
            "Job Title": title.text.strip() if title else None,
            "Company": company.text.strip() if company else None,
            "Location": location.text.strip() if location else None,
            "Summary": summary.text.strip() if summary else None
        }

        job_list.append(job_data)

# Save to CSV
df = pd.DataFrame(job_list)
df.to_csv("indeed_jobs.csv", index=False)
print("Jobs saved to indeed_jobs.csv")
