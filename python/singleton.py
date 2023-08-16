
class SingletonClass(object):
    def __new__(cls):
        if not hasattr(cls, 'instance'):
            cls.instance = super(SingletonClass, cls).__new__(cls)
        return cls.instance
if __name__ == '__main__':
    singleton = SingletonClass()
    new_singleton = SingletonClass()

    print(singleton is new_singleton)

    singleton.singl_variable = "Singleton Variable"
    print(new_singleton.singl_variable)
