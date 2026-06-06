/**
 * 智慧物业管理系统 - 全局前端交互脚本
 */

// 1. Toast 通知系统
const Toast = {
    container: null,
    
    init() {
        if (!this.container) {
            this.container = document.createElement('div');
            this.container.className = 'toast-container';
            document.body.appendChild(this.container);
        }
    },
    
    show(message, type = 'success', duration = 3000) {
        this.init();
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.innerHTML = `
            <span style="font-size: 1.2rem;">${type === 'success' ? '✅' : '❌'}</span>
            <span>${message}</span>
        `;
        this.container.appendChild(toast);
        
        // 触发进场动画
        setTimeout(() => toast.classList.add('show'), 10);
        
        // 自动移除
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        }, duration);
    }
};

// 2. Modal 模态框管理器
const Modal = {
    open(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) modal.classList.add('active');
    },
    close(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) modal.classList.remove('active');
    }
};

// 3. Lightbox 图片预览系统
const Lightbox = {
    overlay: null,
    img: null,
    
    init() {
        if (!this.overlay) {
            this.overlay = document.createElement('div');
            this.overlay.className = 'lightbox-overlay';
            this.overlay.innerHTML = '<img class="lightbox-img" src="">';
            this.overlay.onclick = () => this.close();
            document.body.appendChild(this.overlay);
            this.img = this.overlay.querySelector('img');
        }
    },
    
    preview(src) {
        this.init();
        this.img.src = src;
        this.overlay.style.display = 'flex';
        setTimeout(() => this.overlay.style.opacity = '1', 10);
    },
    
    close() {
        if (this.overlay) {
            this.overlay.style.display = 'none';
        }
    }
};

// 4. 自动挂载事件
document.addEventListener('DOMContentLoaded', () => {
    // 监听所有带 preview 类的图片点击
    document.querySelectorAll('.image-preview, .lightbox-trigger').forEach(el => {
        el.addEventListener('click', (e) => {
            e.preventDefault();
            Lightbox.preview(el.src || el.getAttribute('href'));
        });
    });

    // 监听模态框关闭按钮
    document.querySelectorAll('.modal-close').forEach(btn => {
        btn.addEventListener('click', () => {
            const modal = btn.closest('.modal-overlay');
            if (modal) modal.classList.remove('active');
        });
    });

    // 检查是否有后端传来的操作成功消息 (Thymeleaf)
    const successMsg = document.body.dataset.success;
    if (successMsg) Toast.show(successMsg, 'success');
});

// 5. 动态星级评价逻辑
function initStarRating(containerId, inputName) {
    const container = document.getElementById(containerId);
    if (!container) return;
    
    const stars = container.querySelectorAll('span');
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = inputName;
    input.required = true;
    container.appendChild(input);
    
    stars.forEach(star => {
        star.addEventListener('mouseover', () => {
            const val = star.dataset.value;
            stars.forEach(s => s.classList.toggle('hover', s.dataset.value <= val));
        });
        
        star.addEventListener('mouseout', () => {
            stars.forEach(s => s.classList.remove('hover'));
        });
        
        star.addEventListener('click', () => {
            const val = star.dataset.value;
            input.value = val;
            stars.forEach(s => s.classList.toggle('active', s.dataset.value <= val));
        });
    });
}
