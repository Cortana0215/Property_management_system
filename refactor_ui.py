import os
import glob
import re

user_sidebar = """
    <div class="sidebar">
        <div class="sidebar-header">
            <h2>智慧物业 - 住户端</h2>
        </div>
        <div class="sidebar-menu">
            <a th:href="@{/}">服务大厅</a>
            <a th:href="@{/profile}">个人资料</a>
            <a th:href="@{/report}">设施报修</a>
            <a th:href="@{/complaints}">投诉建议</a>
            <a th:href="@{/notices}">社区公告</a>
            <a th:href="@{/services}">便民服务</a>
            <a th:href="@{/logout}" style="margin-top: 40px; color: #f56565;">退出登录</a>
        </div>
    </div>
    <div class="content">
"""

staff_sidebar = """
    <div class="sidebar">
        <div class="sidebar-header">
            <h2>智慧物业 - 职工端</h2>
        </div>
        <div class="sidebar-menu">
            <a th:href="@{/staff/dashboard}">我的任务</a>
            <a th:href="@{/staff/notices}">内部公告</a>
            <a th:href="@{/logout}" style="margin-top: 40px; color: #f56565;">退出登录</a>
        </div>
    </div>
    <div class="content">
"""

def process_file(filepath, sidebar):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Replace CSS link
    content = re.sub(r'th:href="@\{/css/user\.css.*?\}"', 'th:href="@{/css/admin.css?v=3}"', content)
    
    # Replace header/navbar/container with sidebar
    if 'class="navbar"' in content:
        # User templates typically have:
        # <body>
        #     <div class="navbar">...</div>
        #     <div class="container">
        # Or <div class="container form-container-centered">
        content = re.sub(r'<body>\s*<div class="navbar">.*?</div>\s*<div class="container[^"]*">', '<body>' + sidebar, content, flags=re.DOTALL)
    elif 'class="staff-header"' in content:
        content = re.sub(r'<body>\s*<header class="staff-header">.*?</header>\s*<div class="container[^"]*">', '<body>' + sidebar, content, flags=re.DOTALL)
    else:
        # Fallback if no navbar but has container
        content = re.sub(r'<body>\s*<div class="container[^"]*">', '<body>' + sidebar, content, flags=re.DOTALL)
        
    # Replace action-tile with simple cards if needed, or leave them (admin.css might need some styles, or we can just append them to admin.css)
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

user_files = glob.glob('src/main/resources/templates/user/*.html')
for f in user_files:
    process_file(f, user_sidebar)

staff_files = glob.glob('src/main/resources/templates/staff/*.html')
for f in staff_files:
    process_file(f, staff_sidebar)

print("Refactoring completed.")
