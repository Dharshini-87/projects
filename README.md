# projects
import requests
from bs4 import BeautifulSoup
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import csv
import tkinter as tk
from tkinter import messagebox

def save_to_csv(results, filename):
    """Save product details to a CSV file."""
    with open(filename, mode="w", newline="", encoding="utf-8") as file:
        writer = csv.DictWriter(file, fieldnames=["name", "price", "rating"])
        writer.writeheader()
        for result in results:
            writer.writerow(result)

def scrape_amazon(product):
    """Scrape product details from Amazon."""
    options = Options()
    options.add_argument("--headless")
    driver = webdriver.Chrome(options=options)

    results = []
    try:
        driver.get(f"https://www.amazon.in/s?k={product}")
        WebDriverWait(driver, 10).until(
            EC.presence_of_all_elements_located((By.XPATH, "//div[@data-component-type='s-search-result']"))
        )

        products = driver.find_elements(By.XPATH, "//div[@data-component-type='s-search-result']")

        for product in products[:10]:
            try:
                name = product.find_element(By.XPATH, ".//h2[@class='a-size-medium a-spacing-none a-color-base a-text-normal']").text

                try:
                    price = product.find_element(By.XPATH, ".//span[@class='a-price-whole']").text
                except:
                    price = "Price not available"

                try:
                    rating = product.find_element(By.XPATH, ".//span[@class='a-size-base s-underline-text']").text
                except:
                    rating = "No rating"

                results.append({"name": name, "price": price, "rating": rating})

            except Exception:
                continue  
    except Exception as e:
        print(f"Amazon Error: {e}")

    finally:
        driver.quit()
    return results

def fetch_amazon_products():
    """Fetch product details from Amazon and save them to a CSV."""
    product = entry.get()
    if not product:
        messagebox.showerror("Input Error", "Please enter a product name.")
        return

    text_output.delete(1.0, tk.END)
    text_output.insert(tk.END, "Fetching results from Amazon, please wait...\n")

    amazon_results = scrape_amazon(product)
    save_to_csv(amazon_results, "amazon_products.csv")

    text_output.insert(tk.END, "Amazon Results:\n")
    if amazon_results:
        for result in amazon_results:
            text_output.insert(tk.END, f"Product Name: {result['name']}\n")
            text_output.insert(tk.END, f"Price: {result['price']}\n")
            text_output.insert(tk.END, f"Rating: {result['rating']}\n\n")
    else:
        text_output.insert(tk.END, "No products found on Amazon.\n\n")

# GUI setup
root = tk.Tk()
root.title("Amazon Product Scraper")
root.geometry("800x600")

tk.Label(root, text="Amazon Product Scraper", font=("Arial", 16)).pack(pady=10)
tk.Label(root, text="Enter product name:").pack()

entry = tk.Entry(root, width=50)
entry.pack(pady=5)

tk.Button(root, text="Fetch Amazon Products", command=fetch_amazon_products).pack(pady=10)

text_output = tk.Text(root, height=25, width=80)
text_output.pack()

root.mainloop()
