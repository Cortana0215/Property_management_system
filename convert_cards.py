import glob
import re

def process_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Convert request-card and welcome-card to standard card
    content = content.replace('class="request-card"', 'class="card"')
    content = content.replace('class="welcome-card"', 'class="card"')
    content = content.replace('class="form-container-centered"', 'class="card"')
    content = content.replace('class="notice-list"', 'class="card"')
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

for f in glob.glob('src/main/resources/templates/user/*.html') + glob.glob('src/main/resources/templates/staff/*.html'):
    process_file(f)

print("Card conversion completed.")
