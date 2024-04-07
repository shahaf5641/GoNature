
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager

# Automatically downloads and manages the ChromeDriver binary
driver = webdriver.Chrome(ChromeDriverManager().install())
driver.get("https://www.example.com")
