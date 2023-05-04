import {createContext, Dispatch, PropsWithChildren, SetStateAction, useState} from "react";

/**
 * Interface used to represent the current user.
 */
export interface User {
    id: number;
    username: string;
}

/**
 * Type used to hold user information and a setter to change it.
 */
export type UserContextType = {
    user: User | undefined;
    setUser: Dispatch<SetStateAction<User | undefined>> | undefined;
}

/**
 * User context for all child components.
 */
export const UserContext = createContext<UserContextType | undefined>(undefined);

/**
 * Wrapper component used to store user state for reference by all child components.
 * @param props Component properties, children property is used to construct JSX element.
 */
function UserContextComponent(props: PropsWithChildren) {

    const [user, setUser] = useState<User | undefined>(undefined);

    return (
        <UserContext.Provider value={{user, setUser}}>
            {props.children}
        </UserContext.Provider>
    );
}

export default UserContextComponent;