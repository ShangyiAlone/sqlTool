import chardet

def detect_encoding(file_path):
    with open(file_path, 'rb') as file:
        result = chardet.detect(file.read())
        print(result)
    return result['encoding']


# 用法示例
file_path = './Jtest.java'  # 替换为实际文件路径

encoding = detect_encoding(file_path)

print(f"The file is encoded in: {encoding}")
